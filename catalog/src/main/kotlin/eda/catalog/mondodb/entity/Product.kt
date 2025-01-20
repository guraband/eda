package eda.catalog.mondodb.entity

import eda.common.dto.ProductResponse
import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "product")
class Product(
    @Id
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