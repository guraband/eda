package eda.search.dto

data class ProductTagRequest(
    val productId: Long,
    val tags: List<String>,
)
