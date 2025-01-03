package eda.catalog.cassandra.dto

import eda.catalog.cassandra.entity.Product

data class RegisterProductRequest(
    val sellerId: Long,
    val name: String,
    val description: String,
    val price: Long,
    val stockCount: Int,
    val tags: List<String>
)

data class ProductResponse(
    val id: Long,
    val sellerId: Long,
    val name: String,
    val description: String,
    val price: Long,
    val stockCount: Int,
    val tags: List<String>,
) {
    constructor(product: Product) : this(
        id = product.id,
        sellerId = product.sellerId,
        name = product.name,
        description = product.description,
        price = product.price,
        stockCount = product.stockCount,
        tags = product.tags
    )
}

data class DecreaseStockCountRequest(
    val productId: Long,
    val amount: Int
)