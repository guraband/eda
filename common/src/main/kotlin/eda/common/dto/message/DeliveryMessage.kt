package eda.common.dto.message

import eda.common.enums.DeliveryStatus

class DeliveryRequestMessage(
    val orderId: Long,
    val productName: String,
    val productCount: Int,
    val deliveryId: Long,
    val address: String,
) : Message {
    override fun toString(): String {
        return "DeliveryRequestMessage(orderId=$orderId, productName='$productName', productCount=$productCount, deliveryId=$deliveryId, address=$address)"
    }
}

class DeliveryStatusUpdateMessage(
    val id: Long,
    val orderId: Long,
    val productName: String,
    val productCount: Int,
    val address: String,
    val deliveryStatus: DeliveryStatus,
    val referenceCode: Long,
) : Message {
    override fun toString(): String {
        return "DeliveryResponseMessage(id=$id, orderId=$orderId, productName='$productName', productCount=$productCount, address=$address, deliveryStatus=$deliveryStatus, referenceCode=$referenceCode)"
    }
}