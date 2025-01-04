package eda.payment.service

import eda.common.dto.PaymentMethodResponse
import eda.common.dto.PaymentRequest
import eda.common.dto.PaymentResponse
import eda.payment.dto.PaymentMethodRequest
import eda.payment.entity.Payment
import eda.payment.entity.PaymentMethod
import eda.common.enums.PaymentMethodType
import eda.common.enums.PaymentStatus
import eda.payment.pg.CreditCardPaymentAdapter
import eda.payment.repository.PaymentMethodRepository
import eda.payment.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val paymentMethodRepository: PaymentMethodRepository,
    private val creditCardPaymentAdapter: CreditCardPaymentAdapter,
) {
    fun registerPaymentMethod(request: PaymentMethodRequest): PaymentMethod {
        val paymentMethod = PaymentMethod(
            userId = request.userId,
            paymentMethodType = request.paymentMethodType,
            creditCardNumber = request.creditCardNumber,
        )
        paymentMethodRepository.save(paymentMethod)
        return paymentMethod
    }

    fun processPayment(
        request: PaymentRequest,
    ): Payment {
        val paymentMethod = paymentMethodRepository.findById(request.paymentMethodId)
            .orElseThrow { throw IllegalArgumentException("Payment method not found") }

        if (paymentMethod.paymentMethodType != PaymentMethodType.CREDIT_CARD) {
            throw IllegalArgumentException("Unsupported payment method type")
        }

        val referenceCode = creditCardPaymentAdapter.processPayment(
            creditCardNumber = paymentMethod.creditCardNumber,
            amount = request.amount,
        )

        val payment = Payment(
            userId = request.userId,
            orderId = request.orderId,
            amount = request.amount,
            paymentMethodId = request.paymentMethodId,
            paymentStatus = PaymentStatus.COMPLETED,
            referenceCode = referenceCode,
        )
        paymentRepository.save(payment)

        return payment
    }

    fun getPaymentMethod(userId: Long): PaymentMethodResponse {
        return paymentMethodRepository.findAllByUserId(userId).firstOrNull()
            ?.let { PaymentMethodResponse(it.id!!, it.paymentMethodType, it.creditCardNumber) }
            ?: throw IllegalArgumentException("Payment method not found")
    }

    fun getPayment(paymentId: Long): PaymentResponse {
        return paymentRepository.findById(paymentId)
            .map { PaymentResponse(it.orderId, it.amount, it.referenceCode) }
            .orElseThrow { throw IllegalArgumentException("Payment not found") }
    }
}