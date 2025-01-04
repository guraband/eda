package eda.order.dto

import eda.order.enums.PaymentMethodType

data class AddressResponse(
    val id: Long,
    val userId: Long,
    val address: String,
    val alias: String,
)