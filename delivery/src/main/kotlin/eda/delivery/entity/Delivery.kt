package eda.delivery.entity

import eda.delivery.enums.DeliveryStatus
import jakarta.persistence.*

@Entity
@Table(name = "delivery", indexes = [Index(name = "idx_orderid", columnList = "order_id")])
class Delivery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val orderId: Long,

    val productName: String,

    val productCount: Int,

    val addressId: Long,

    deliveryStatus: DeliveryStatus,

    val referenceCode: Long,
) {
    @Enumerated(EnumType.STRING)
    var deliveryStatus: DeliveryStatus = deliveryStatus
        protected set

    fun updateStatus() {
        when(this.deliveryStatus) {
            DeliveryStatus.REQUESTED -> this.deliveryStatus = DeliveryStatus.IN_DELIVERY
            DeliveryStatus.IN_DELIVERY -> this.deliveryStatus = DeliveryStatus.COMPLETED
            DeliveryStatus.COMPLETED -> return
        }
    }
}