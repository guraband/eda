package eda.payment.entity

import eda.common.enums.PaymentMethodType
import jakarta.persistence.*

@Entity
@Table(name = "payment_method", indexes = [Index(name = "idx_userid", columnList = "user_id")])
class PaymentMethod(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val userId: Long,

    @Enumerated(EnumType.STRING)
    val paymentMethodType: PaymentMethodType,

    val creditCardNumber: String,
) {
}