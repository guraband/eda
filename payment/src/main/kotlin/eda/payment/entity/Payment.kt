package eda.payment.entity

import eda.common.enums.PaymentStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment", indexes = [Index(name = "idx_userid", columnList = "user_id")])
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val userId: Long,

    @Column(unique = true)
    val orderId: Long,

    val amount: Long,

    val paymentMethodId: Long,

    val paymentData: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    val paymentStatus: PaymentStatus,

    @Column(unique = true)
    val referenceCode: Long?,
) {
}