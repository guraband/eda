package eda.delivery.service

import eda.common.dto.DeliveryRequest
import eda.common.dto.message.DeliveryRequestMessage
import eda.common.dto.message.DeliveryStatusUpdateMessage
import eda.common.dto.message.Message
import eda.common.enums.MessageTopic
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class EventListener(
    private val deliveryService: DeliveryService,
    private val jsonKafkaTemplate: KafkaTemplate<String, Message>,
) {
    companion object {
        const val TOPIC_DELIVERY_REQUEST = "delivery_request"
    }

    @KafkaListener(topics = [TOPIC_DELIVERY_REQUEST], containerFactory = "jsonKafkaListenerContainerFactory")
    fun consumeDeliveryRequest(message: DeliveryRequestMessage) {
        println(">>> $message")
        val deliveryResponse = deliveryService.process(
            DeliveryRequest(
                orderId = message.orderId,
                productName = message.productName,
                productCount = message.productCount,
                addressId = message.addressId,
            )
        )

        val deliveryStatusUpdateMessage = DeliveryStatusUpdateMessage(
            orderId = deliveryResponse.orderId,
            productName = deliveryResponse.productName,
            productCount = deliveryResponse.productCount,
            addressId = deliveryResponse.addressId,
            deliveryStatus = deliveryResponse.deliveryStatus,
            referenceCode = deliveryResponse.referenceCode,
        )

        jsonKafkaTemplate.send(MessageTopic.DELIVERY_STATUS_UPDATE.topicName, deliveryStatusUpdateMessage)
    }
}