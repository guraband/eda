package eda.payment.dto

data class PaymentRequest(
    val userId: Long,
    val orderId: Long,
    val amount: Long,
    val paymentMethodId: Long,
)

data class PaymentResponse(
    val orderId: Long,
    val amount: Long,
    val referenceCode: Long?,
)
