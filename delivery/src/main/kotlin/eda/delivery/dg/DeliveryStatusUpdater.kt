package eda.delivery.dg

import eda.delivery.repository.DeliveryRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DeliveryStatusUpdater(
    private val deliveryRepository: DeliveryRepository,
) {
    @Scheduled(fixedDelay = 10_000)
    fun updateStatus() {
        deliveryRepository.findAll().forEach {
            it.updateStatus()
        }
    }
}