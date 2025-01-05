package eda.order.service

import eda.common.dto.DecreaseStockCountRequest
import eda.common.dto.DeliveryRequest
import eda.common.dto.PaymentRequest
import eda.common.enums.OrderStatus
import eda.order.dto.*
import eda.order.entity.ProductOrder
import eda.order.feign.CatalogClient
import eda.order.feign.DeliveryClient
import eda.order.feign.PaymentClient
import eda.order.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val paymentClient: PaymentClient,
    private val deliveryClient: DeliveryClient,
    private val catalogClient: CatalogClient,
) {
    fun startOrder(
        request: StartOrderRequest,
    ): StartOrderResponse {
        // 1. 상품 정보 조회
        val product = catalogClient.getProductById(request.productId)

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

    fun finishOrder(
        request: FinishOrderRequest
    ): FinishOrderResponse {
        val order = orderRepository.findById(request.orderId)
            .orElseThrow { throw IllegalArgumentException("주문이 존재하지 않습니다.") }

        // 1. 상품 정보 조회
        val product = catalogClient.getProductById(order.productId)

        // 2. 결제
        val paymentRequest = PaymentRequest(
            userId = order.userId,
            orderId = order.id!!,
            amount = product.price * order.quantity,
            paymentMethodId = request.paymentMethodId,
        )

        val paymentResponse = paymentClient.processPayment(paymentRequest)

        // 3. 배송
        val deliveryRequest = DeliveryRequest(
            orderId = order.id!!,
            productName = product.name,
            productCount = order.quantity,
            addressId = request.addressId,
        )

        val deliveryResponse = deliveryClient.processDelivery(deliveryRequest)

        // 4. 상품 재고 차감
        catalogClient.decreaseStockCount(
            DecreaseStockCountRequest(
                productId = order.productId,
                amount = order.quantity,
            )
        )

        // 5. 주문 정보 업데이트
        order.updateOrder(paymentResponse.id, deliveryResponse.id, OrderStatus.DELIVERY_REQUESTED)

        orderRepository.save(order)

        return order.toResponseDto()
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