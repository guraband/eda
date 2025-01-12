package eda.common.enums

enum class MessageTopic(
    val topicName: String,
) {
    PRODUCT_TAGS_ADDED("product_tags_added"),
    PRODUCT_TAGS_REMOVED("product_tags_removed"),
}