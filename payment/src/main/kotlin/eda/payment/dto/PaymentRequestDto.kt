package eda.payment.dto

import eda.common.enums.PaymentMethodType

data class PaymentMethodRequest(
    val userId: Long,
    val paymentMethodType: PaymentMethodType,
    val creditCardNumber: String,
)