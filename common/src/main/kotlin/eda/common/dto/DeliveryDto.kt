package eda.common.dto

import eda.common.enums.DeliveryStatus

data class DeliveryRequest(
    val orderId: Long,
    val productName: String,
    val productCount: Int,
    val addressId: Long,
)

data class DeliveryResponse(
    val id: Long,
    val orderId: Long,
    val productName: String,
    val productCount: Int,
    val addressId: Long,
    val deliveryStatus: DeliveryStatus,
    val referenceCode: Long,
)