package eda.common.dto

class ProductTagRequest(
    val productId: Long,
    val tags: List<String>,
) : Message