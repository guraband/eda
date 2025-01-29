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
    deliveryAddress: String? = null,
) {
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = orderStatus
        protected set

    var paymentId: Long? = paymentId
        protected set

    var deliveryId: Long? = deliveryId
        protected set

    var deliveryAddress: String? = deliveryAddress
        protected set

    fun updateOrder(
        paymentId: Long? = null,
        deliveryId: Long? = null,
        deliveryAddress: String? = null,
        orderStatus: OrderStatus? = null,
    ) {
        paymentId?.let { this.paymentId = it }
        deliveryId?.let { this.deliveryId = it }
        deliveryAddress?.let { this.deliveryAddress = it }
        orderStatus?.let { this.orderStatus = it }
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