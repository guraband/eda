package eda.catalog.mariadb.entity

import jakarta.persistence.*

@Entity
@Table(name = "seller_product")
class SellerProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val sellerId: Long,
) {
}