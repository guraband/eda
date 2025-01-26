package eda.search.service

import eda.common.dto.ProductTagRequest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val stringRedisTemplate: RedisTemplate<String, String>
) {
    fun addTagCache(
        request: ProductTagRequest,
    ) {
        val ops = stringRedisTemplate.opsForSet()
        request.tags.forEach { tag ->
            ops.add(tag, request.productId.toString())
        }
    }

    fun removeTagCache(
        request: ProductTagRequest,
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