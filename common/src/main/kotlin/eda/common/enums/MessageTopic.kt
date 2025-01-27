package eda.common.enums

enum class MessageTopic(
    val topicName: String,
) {
    PRODUCT_TAGS_ADDED("product_tags_added"),
    PRODUCT_TAGS_REMOVED("product_tags_removed"),
    PAYMENT_REQUEST("payment_request"),
    PAYMENT_RESULT("payment_result"),
    DELIVERY_REQUEST("delivery_request"),
    DELIVERY_STATUS_UPDATE("delivery_status_update"),
}