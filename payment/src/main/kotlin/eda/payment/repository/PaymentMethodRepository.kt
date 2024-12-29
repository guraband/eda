package eda.payment.repository

import eda.payment.entity.PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentMethodRepository : JpaRepository<PaymentMethod, Long> {
    fun findAllByUserId(userId: Long): List<PaymentMethod>
}