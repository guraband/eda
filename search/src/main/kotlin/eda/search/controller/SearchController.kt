package eda.search.controller

import eda.search.dto.ProductTagRequest
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
        @RequestBody request: ProductTagRequest,
    ): ResponseEntity<Unit> {
        searchService.addTagCache(request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/tag")
    fun removeTagCache(
        @RequestBody request: ProductTagRequest,
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