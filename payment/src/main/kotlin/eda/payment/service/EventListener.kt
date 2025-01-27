package eda.payment.service

import eda.common.dto.PaymentRequest
import eda.common.dto.message.Message
import eda.common.dto.message.PaymentRequestMessage
import eda.common.dto.message.PaymentResultMessage
import eda.common.enums.MessageTopic
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class EventListener(
    private val paymentService: PaymentService,
    private val jsonKafkaTemplate: KafkaTemplate<String, Message>
) {
    companion object {
        const val TOPIC_PAYMENT_REQUEST = "payment_request"
    }

    @KafkaListener(topics = [TOPIC_PAYMENT_REQUEST], containerFactory = "jsonKafkaListenerContainerFactory")
    fun consumePaymentRequest(message: PaymentRequestMessage) {
        println(">>> $message")
        val paymentResponse = paymentService.processPayment(
            PaymentRequest(
                userId = message.userId,
                orderId = message.orderId,
                amount = message.amount,
                paymentMethodId = message.paymentMethodId,
            )
        )

        val resultMessage = PaymentResultMessage(
            id = paymentResponse.id,
            orderId = paymentResponse.orderId,
            amount = paymentResponse.amount,
            referenceCode = paymentResponse.referenceCode,
            paymentStatus = paymentResponse.paymentStatus,
        )

        jsonKafkaTemplate.send(MessageTopic.PAYMENT_RESULT.topicName, resultMessage)
    }
}