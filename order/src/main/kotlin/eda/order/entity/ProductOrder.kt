package eda.order.entity

import eda.common.enums.OrderStatus
import jakarta.persistence.*

@Entity
@Table(name = "product_order", indexes = [Index(name = "idx_userid", columnList = "user_id")])
class ProductOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val userId: Long,

    val productId: Long,
    val quantity: Int,

    orderStatus: OrderStatus,

    val paymentId: Long,
    val deliveryId: Long,
) {
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = orderStatus
        protected set
}