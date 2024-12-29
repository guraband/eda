package eda.payment.pg

interface CreditCardPaymentAdapter {
    fun processPayment(
        amount: Long,
        creditCardNumber: String,
    ): Long
}