package eda.catalog.mariadb.repository

import eda.catalog.mariadb.entity.SellerProduct
import org.springframework.data.jpa.repository.JpaRepository

interface SellerProductRepository : JpaRepository<SellerProduct, Long> {
    fun findBySellerId(sellerId: Long): List<SellerProduct>
}