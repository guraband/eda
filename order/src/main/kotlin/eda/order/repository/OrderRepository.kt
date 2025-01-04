package eda.order.repository

import eda.order.entity.ProductOrder
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<ProductOrder, Long> {
    fun findAllByUserId(userId: Long): List<ProductOrder>
}