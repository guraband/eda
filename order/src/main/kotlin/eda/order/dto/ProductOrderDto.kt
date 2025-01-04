package eda.order.dto

import eda.order.enums.OrderStatus

data class ProductOrderDto(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val quantity: Int,
    val orderStatus: OrderStatus,
    val paymentId: Long,
    val deliveryId: Long,

    val paymentStatusName: String,
    val deliveryStatusName: String,
)
