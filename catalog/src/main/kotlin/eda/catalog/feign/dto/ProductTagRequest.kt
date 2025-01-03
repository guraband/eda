package eda.catalog.feign.dto

data class ProductTagRequest(
    val productId: Long,
    val tags: List<String>,
)