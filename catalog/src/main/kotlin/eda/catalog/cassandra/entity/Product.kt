package eda.catalog.cassandra.entity

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table
class Product(
    @PrimaryKey
    val id: Long,

    val sellerId: Long,

    val name: String,

    val description: String,

    val price: Long,

    private var _stockCount: Int,

    val tags: List<String>,
) {
    val stockCount: Int // public read-only 프로퍼티
        get() = _stockCount

    fun decreaseStockCount(amount: Int) {
        if (_stockCount - amount < 0) {
            throw RuntimeException("Stock count cannot be negative")
        }
        _stockCount -= amount
    }
}