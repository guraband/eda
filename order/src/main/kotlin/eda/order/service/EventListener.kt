package eda.order.service

import eda.common.dto.DeliveryResponse
import eda.common.dto.PaymentResponse
import eda.common.dto.message.DeliveryStatusUpdateMessage
import eda.common.dto.message.PaymentResultMessage
import eda.common.enums.DeliveryStatus
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class EventListener(
    private val orderService: OrderService,
) {
    companion object {
        const val TOPIC_PAYMENT_RESULT = "payment_result"
        const val TOPIC_DELIVERY_STATUS_UPDATE = "delivery_status_update"
    }

    @KafkaListener(topics = [TOPIC_PAYMENT_RESULT], containerFactory = "jsonKafkaListenerContainerFactory")
    fun consumePaymentResult(message: PaymentResultMessage) {
        println(">>> $message")

        val paymentResponse = PaymentResponse(
            id = message.id,
            orderId = message.orderId,
            amount = message.amount,
            referenceCode = message.referenceCode,
            paymentStatus = message.paymentStatus,
        )
        orderService.executeOnPaymentSuccess(paymentResponse)
    }

    @KafkaListener(topics = [TOPIC_DELIVERY_STATUS_UPDATE], containerFactory = "jsonKafkaListenerContainerFactory")
    fun consumeDeliveryStatusUpdate(message: DeliveryStatusUpdateMessage) {
        println(">>> $message")

        if (message.deliveryStatus != DeliveryStatus.REQUESTED) {
            return
        }

        val deliveryResponse = DeliveryResponse(
            id = message.id,
            orderId = message.orderId,
            productName = message.productName,
            productCount = message.productCount,
            address = message.address,
            deliveryStatus = message.deliveryStatus,
            referenceCode = message.referenceCode,
        )
        orderService.executeOnDeliveryStatusUpdate(deliveryResponse)
    }
}