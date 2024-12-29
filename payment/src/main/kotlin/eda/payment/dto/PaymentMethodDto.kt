package eda.payment.dto

import eda.payment.enums.PaymentMethodType

data class PaymentMethodRequest(
    val userId: Long,
    val paymentMethodType: PaymentMethodType,
    val creditCardNumber: String,
)

data class PaymentMethodResponse(
    val id: Long,
    val paymentMethodType: PaymentMethodType,
    val creditCardNumber: String,
)