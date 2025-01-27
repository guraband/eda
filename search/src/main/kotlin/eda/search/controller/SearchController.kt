package eda.search.controller

import eda.common.dto.message.ProductTagMessage
import eda.search.service.SearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/search")
class SearchController(
    private val searchService: SearchService,
) {

    @PostMapping("/tag")
    fun addTagCache(
        @RequestBody request: ProductTagMessage,
    ): ResponseEntity<Unit> {
        searchService.addTagCache(request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/tag")
    fun removeTagCache(
        @RequestBody request: ProductTagMessage,
    ): ResponseEntity<Unit> {
        searchService.removeTagCache(request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/tag/{tag}/productIds")
    fun getProductIdsByTag(
        @PathVariable tag: String,
    ): ResponseEntity<List<Long>> {
        return ResponseEntity.ok(
            searchService.getProductIdsByTag(tag)
        )
    }
}