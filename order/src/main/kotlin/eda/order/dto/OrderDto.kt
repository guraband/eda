package eda.order.dto

import eda.common.dto.AddressResponse
import eda.common.dto.PaymentMethodResponse


data class StartOrderRequest(
    val userId: Long,
    val productId: Long,
    val quantity: Long,
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
