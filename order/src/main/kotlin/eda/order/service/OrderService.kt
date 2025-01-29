package eda.order.service

import eda.common.dto.*
import eda.common.dto.message.*
import eda.common.enums.DeliveryStatus
import eda.common.enums.MessageTopic
import eda.common.enums.OrderStatus
import eda.order.dto.*
import eda.order.entity.ProductOrder
import eda.order.feign.CatalogClient
import eda.order.feign.DeliveryClient
import eda.order.feign.PaymentClient
import eda.order.repository.OrderRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val paymentClient: PaymentClient,
    private val deliveryClient: DeliveryClient,
    private val catalogClient: CatalogClient,
    private val kafkaTemplate: KafkaTemplate<String, Message>,
) {
    fun startOrder(
        request: StartOrderRequest,
    ): StartOrderResponse {
        // 1. 상품 정보 조회
        catalogClient.getProductById(request.productId)

        // 2. 결제 수단 조회
        val paymentMethod = paymentClient.getPaymentMethod(request.userId)

        // 3. 배송지 조회
        val address = deliveryClient.getAddressByUser(request.userId)

        // 4. 주문 생성
        val order = ProductOrder(
            userId = request.userId,
            productId = request.productId,
            quantity = request.quantity,
            orderStatus = OrderStatus.INITIATED,
        )

        orderRepository.save(order)

        return StartOrderResponse(
            orderId = order.id!!,
            paymentMethod = paymentMethod,
            address = address,
        )
    }

    @Transactional
    fun finishOrder(
        request: FinishOrderRequest
    ): FinishOrderResponse {
        val order = orderRepository.findById(request.orderId)
            .orElseThrow { throw IllegalArgumentException("주문이 존재하지 않습니다.") }

        // 1. 상품 정보 조회
        val product = catalogClient.getProductById(order.productId)

        // 2. 결제
        val paymentRequestMessage = PaymentRequestMessage(
            userId = order.userId,
            orderId = order.id!!,
            amount = product.price * order.quantity,
            paymentMethodId = request.paymentMethodId,
        )

        kafkaTemplate.send(MessageTopic.PAYMENT_REQUEST.topicName, paymentRequestMessage)

        // 3. 주문 정보 업데이트
        val address = deliveryClient.getAddress(request.addressId)
        order.updateOrder(
            deliveryAddress = address.address,
            orderStatus = OrderStatus.PAYMENT_REQUESTED
        )
        orderRepository.save(order)

        return order.toResponseDto()
    }

    @Transactional
    fun executeOnPaymentSuccess(paymentResponse: PaymentResponse) {
        // 1. 주문 정보 업데이트
        val order = orderRepository.findById(paymentResponse.orderId)
            .orElseThrow { throw IllegalArgumentException("주문이 존재하지 않습니다.") }
        order.updateOrder(
            paymentId = paymentResponse.id,
            orderStatus = OrderStatus.DELIVERY_REQUESTED,
        )

        // 2. 배송
        val product = catalogClient.getProductById(order.productId)
        val deliveryRequestMessage = DeliveryRequestMessage(
            orderId = order.id!!,
            productName = product.name,
            productCount = order.quantity,
            deliveryId = order.deliveryId!!,
            address = order.deliveryAddress!!,
        )

        kafkaTemplate.send(MessageTopic.DELIVERY_REQUEST.topicName, deliveryRequestMessage)
    }

    @Transactional
    fun executeOnDeliveryStatusUpdate(deliveryResponse: DeliveryResponse) {
        // 1. 주문 정보 업데이트
        val order = orderRepository.findById(deliveryResponse.orderId)
            .orElseThrow { throw IllegalArgumentException("주문이 존재하지 않습니다.") }
        order.updateOrder(
            deliveryId = deliveryResponse.id,
        )

        // 2. 상품 재고 차감
        catalogClient.decreaseStockCount(
            DecreaseStockCountRequest(
                productId = order.productId,
                amount = order.quantity,
            )
        )
    }

    fun getUserOrders(userId: Long): List<FinishOrderResponse> {
        return orderRepository.findAllByUserId(userId)
            .map { it.toResponseDto() }
    }

    fun getOrderDetail(orderId: Long): ProductOrderDetailDto {
        val order = orderRepository.findById(orderId)
            .orElseThrow { throw IllegalArgumentException("주문이 존재하지 않습니다.") }

        val paymentResponse = paymentClient.getPayment(order.paymentId!!)
        val deliveryResponse = deliveryClient.getDelivery(order.deliveryId!!)

        return ProductOrderDetailDto(
            id = order.id!!,
            userId = order.userId,
            productId = order.productId,
            quantity = order.quantity,
            orderStatus = order.orderStatus,
            paymentId = order.paymentId!!,
            deliveryId = order.deliveryId!!,
            paymentStatusName = paymentResponse.paymentStatus.name,
            deliveryStatusName = deliveryResponse.deliveryStatus.name,
        )
    }
}