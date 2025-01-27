package eda.common.dto.message

import eda.common.enums.DeliveryStatus

class DeliveryRequestMessage(
    val orderId: Long,
    val productName: String,
    val productCount: Int,
    val addressId: Long,
) : Message {
    override fun toString(): String {
        return "DeliveryRequestMessage(orderId=$orderId, productName='$productName', productCount=$productCount, addressId=$addressId)"
    }
}

class DeliveryStatusUpdateMessage(
    val orderId: Long,
    val productName: String,
    val productCount: Int,
    val addressId: Long,
    val deliveryStatus: DeliveryStatus,
    val referenceCode: Long,
) : Message {
    override fun toString(): String {
        return "DeliveryResponseMessage(orderId=$orderId, productName='$productName', productCount=$productCount, addressId=$addressId, deliveryStatus=$deliveryStatus, referenceCode=$referenceCode)"
    }
}