package eda.common.dto

import eda.common.enums.DeliveryStatus

data class DeliveryRequest(
    val orderId: Long,
    val productName: String,
    val productCount: Int,
    val deliveryId: Long,
    val address: String,
)

data class DeliveryResponse(
    val id: Long,
    val orderId: Long,
    val productName: String,
    val productCount: Int,
    val address: String,
    val deliveryStatus: DeliveryStatus,
    val referenceCode: Long,
)