package eda.order.dto

import eda.common.dto.AddressResponse
import eda.common.dto.PaymentMethodResponse
import eda.common.enums.OrderStatus


data class StartOrderRequest(
    val userId: Long,
    val productId: Long,
    val quantity: Int,
)

data class StartOrderResponse(
    val orderId: Long,
    val paymentMethod: PaymentMethodResponse,
    val address: AddressResponse,
)

data class FinishOrderRequest(
    val orderId: Long,
    val paymentMethodId: Long,
    val addressId: Long,
)

data class FinishOrderResponse(
    val orderId: Long,
    val userId: Long,
    val productId: Long,
    val quantity: Int,
    val orderStatus: OrderStatus,
    val paymentId: Long,
    val deliveryId: Long,
)
