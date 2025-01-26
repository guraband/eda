package eda.common.dto

class ProductTagRequest(
    val productId: Long,
    val tags: List<String>,
) : Message {
    override fun toString(): String {
        return "ProductTagRequest(productId=$productId, tags=$tags)"
    }
}