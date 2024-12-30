package eda.delivery.dto

import eda.delivery.entity.Delivery
import eda.delivery.enums.DeliveryStatus

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
) {
    constructor(delivery: Delivery) : this(
        id = delivery.id!!,
        orderId = delivery.orderId,
        productName = delivery.productName,
        productCount = delivery.productCount,
        addressId = delivery.addressId,
        deliveryStatus = delivery.deliveryStatus,
        referenceCode = delivery.referenceCode,
    )
}
