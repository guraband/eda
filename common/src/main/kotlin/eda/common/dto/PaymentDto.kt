package eda.common.dto

import eda.common.enums.PaymentMethodType
import eda.common.enums.PaymentStatus

data class PaymentMethodResponse(
    val id: Long,
    val paymentMethodType: PaymentMethodType,
    val creditCardNumber: String,
)

data class PaymentRequest(
    val userId: Long,
    val orderId: Long,
    val amount: Long,
    val paymentMethodId: Long,
)

data class PaymentResponse(
    val id: Long,
    val orderId: Long,
    val amount: Long,
    val referenceCode: Long?,
    val paymentStatus: PaymentStatus,
)