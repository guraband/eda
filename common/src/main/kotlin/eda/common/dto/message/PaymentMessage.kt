package eda.common.dto.message

import eda.common.enums.PaymentStatus

class PaymentRequestMessage(
    val userId: Long,
    val orderId: Long,
    val amount: Long,
    val paymentMethodId: Long,
) : Message {
    override fun toString(): String {
        return "PaymentRequestMessage(userId=$userId, orderId=$orderId, amount=$amount, paymentMethodId=$paymentMethodId)"
    }
}

class PaymentResultMessage(
    val id: Long,
    val orderId: Long,
    val amount: Long,
    val referenceCode: Long?,
    val paymentStatus: PaymentStatus,
) : Message {
    override fun toString(): String {
        return "PaymentResponseMessage(id=$id, orderId=$orderId, amount=$amount, referenceCode=$referenceCode, paymentStatus=$paymentStatus)"
    }
}