package eda.catalog.feign

import eda.common.dto.ProductTagRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "searchClient", url = "http://localhost:8084/v1/search")
interface SearchClient {
    @PostMapping("/tag")
    fun addTagCache(@RequestBody request: ProductTagRequest)

    @DeleteMapping("/tag")
    fun removeTagCache(@RequestBody request: ProductTagRequest)
}