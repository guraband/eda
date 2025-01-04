package eda.common.dto

data class DecreaseStockCountRequest(
    val productId: Long,
    val amount: Int
)

data class ProductResponse(
    val id: Long,
    val sellerId: Long,
    val name: String,
    val description: String,
    val price: Long,
    val stockCount: Int,
    val tags: List<String>,
)
