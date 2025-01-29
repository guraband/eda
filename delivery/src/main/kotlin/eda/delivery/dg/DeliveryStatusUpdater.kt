package eda.delivery.dg

import eda.common.dto.message.DeliveryStatusUpdateMessage
import eda.common.dto.message.Message
import eda.delivery.entity.Delivery
import eda.delivery.repository.DeliveryRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DeliveryStatusUpdater(
    private val deliveryRepository: DeliveryRepository,
    private val jsonKafkaTemplate: KafkaTemplate<String, Message>,
) {
    @Scheduled(fixedDelay = 10_000)
    fun updateStatus() {
        deliveryRepository.findAll().forEach {
            it.updateStatus()
            publishStatusUpdate(it)
        }
    }

    private fun publishStatusUpdate(delivery: Delivery) {
        val message = DeliveryStatusUpdateMessage(
            id = delivery.id!!,
            orderId = delivery.orderId,
            productName = delivery.productName,
            productCount = delivery.productCount,
            address = delivery.address,
            deliveryStatus = delivery.deliveryStatus,
            referenceCode = delivery.referenceCode,
        )

        jsonKafkaTemplate.send("delivery_status_update", message)
    }
}