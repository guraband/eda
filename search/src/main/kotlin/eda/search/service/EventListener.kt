package eda.search.service

import eda.common.dto.message.ProductTagMessage
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class EventListener(
    private val searchService: SearchService,
) {

    companion object {
        const val TOPIC_PRODUCT_TAGS_ADDED = "product_tags_added"
        const val TOPIC_PRODUCT_TAGS_REMOVED = "product_tags_removed"
    }
    @KafkaListener(topics = [TOPIC_PRODUCT_TAGS_ADDED], containerFactory = "jsonKafkaListenerContainerFactory")
    fun consumeTagsAdded(request: ProductTagMessage) {
        println(">>> $request")
        searchService.addTagCache(request)
    }

    @KafkaListener(topics = [TOPIC_PRODUCT_TAGS_REMOVED], containerFactory = "jsonKafkaListenerContainerFactory")
    fun consumeTagsRemoved(request: ProductTagMessage) {
        println(">>> $request")
        searchService.removeTagCache(request)
    }
}