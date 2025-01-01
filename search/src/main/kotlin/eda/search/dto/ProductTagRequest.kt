package eda.search.dto

data class AddTagRequest(
    val productId: Long,
    val tags: List<String>,
)
