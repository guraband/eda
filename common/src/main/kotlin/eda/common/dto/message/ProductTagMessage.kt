package eda.common.dto.message

class ProductTagMessage(
    val productId: Long,
    val tags: List<String>,
) : Message {
    override fun toString(): String {
        return "ProductTagMessage(productId=$productId, tags=$tags)"
    }
}