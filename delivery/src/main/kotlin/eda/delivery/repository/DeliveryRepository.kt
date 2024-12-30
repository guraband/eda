package eda.delivery.repository

import eda.delivery.entity.Delivery
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryRepository : JpaRepository<Delivery, Long> {
    fun findAllByOrderId(orderId: Long): List<Delivery>
}