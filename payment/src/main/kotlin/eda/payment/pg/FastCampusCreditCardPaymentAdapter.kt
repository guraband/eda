package eda.payment.pg

import org.springframework.stereotype.Component

@Component
class FastCampusCreditCardPaymentAdapter : CreditCardPaymentAdapter {
    override fun processPayment(amount: Long, creditCardNumber: String): Long {
        println("FastCampusCreditCardPaymentAdapter.processPayment")
        return Math.round(Math.random() * 1_000_000L)
    }
}