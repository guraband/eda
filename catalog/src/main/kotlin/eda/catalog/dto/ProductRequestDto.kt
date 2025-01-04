package eda.catalog.dto

data class RegisterProductRequest(
    val sellerId: Long,
    val name: String,
    val description: String,
    val price: Long,
    val stockCount: Int,
    val tags: List<String>
)
