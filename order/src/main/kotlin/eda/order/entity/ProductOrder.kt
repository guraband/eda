package eda.order.entity

import eda.common.enums.OrderStatus
import eda.order.dto.FinishOrderResponse
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

    paymentId: Long? = null,
    deliveryId: Long? = null,
) {
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = orderStatus
        protected set

    var paymentId: Long? = paymentId
        protected set

    var deliveryId: Long? = deliveryId
        protected set

    fun updateOrder(paymentId: Long, deliveryId: Long, orderStatus: OrderStatus) {
        this.paymentId = paymentId
        this.deliveryId = deliveryId
        this.orderStatus = orderStatus
    }

    fun toResponseDto(): FinishOrderResponse {
        return FinishOrderResponse(
            orderId = this.id!!,
            userId = this.userId,
            productId = this.productId,
            quantity = this.quantity,
            orderStatus = this.orderStatus,
            paymentId = this.paymentId!!,
            deliveryId = this.deliveryId!!,
        )
    }
}