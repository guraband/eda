package eda.search.service

import eda.common.dto.message.ProductTagMessage
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val stringRedisTemplate: RedisTemplate<String, String>
) {
    fun addTagCache(
        request: ProductTagMessage,
    ) {
        val ops = stringRedisTemplate.opsForSet()
        request.tags.forEach { tag ->
            ops.add(tag, request.productId.toString())
        }
    }

    fun removeTagCache(
        request: ProductTagMessage,
    ) {
        val ops = stringRedisTemplate.opsForSet()
        request.tags.forEach { tag ->
            ops.remove(tag, request.productId.toString())
        }
    }

    fun getProductIdsByTag(
        tag: String,
    ): List<Long> {
        val ops = stringRedisTemplate.opsForSet()
        return ops.members(tag)?.map { it.toLong() } ?: emptyList()
    }
}