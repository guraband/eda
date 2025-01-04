package eda.common.dto

data class ProductTagRequest(
    val productId: Long,
    val tags: List<String>,
)