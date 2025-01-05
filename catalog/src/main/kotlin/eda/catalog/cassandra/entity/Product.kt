package eda.catalog.cassandra.entity

import eda.common.dto.ProductResponse
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

    stockCount: Int,

    val tags: List<String>,
) {
    var stockCount: Int = stockCount
        protected set

    fun decreaseStockCount(amount: Int) {
        if (this.stockCount - amount < 0) {
            throw RuntimeException("Stock count cannot be negative")
        }
        this.stockCount -= amount
    }

    fun toResponseDto() : ProductResponse {
        return ProductResponse(
            id = id,
            sellerId = sellerId,
            name = name,
            description = description,
            price = price,
            stockCount = stockCount,
            tags = tags
        )
    }
}