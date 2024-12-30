package eda.delivery.entity

import jakarta.persistence.*

@Entity
@Table(name = "address", indexes = [Index(name = "idx_userid", columnList = "user_id")])
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val userId: Long,

    val address: String,

    val alias: String,
) {
}