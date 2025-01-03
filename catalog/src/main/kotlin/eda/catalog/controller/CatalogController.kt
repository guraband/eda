package eda.catalog.controller

import eda.catalog.dto.DecreaseStockCountRequest
import eda.catalog.dto.ProductResponse
import eda.catalog.dto.RegisterProductRequest
import eda.catalog.service.CatalogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/catalog")
class CatalogController(
    private val catalogService: CatalogService,
) {

    @PostMapping("/product")
    fun registerProduct(
        @RequestBody request: RegisterProductRequest
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(
            catalogService.registerProduct(request)
        )
    }

    @DeleteMapping("/product/{productId}")
    fun deleteProduct(
        @PathVariable productId: Long
    ): ResponseEntity<Unit> {
        catalogService.deleteProduct(productId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/product/{productId}")
    fun getProductById(
        @PathVariable productId: Long
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(
            catalogService.getProductById(productId)
        )
    }

    @GetMapping("/seller/{sellerId}/products")
    fun getProductsBySellerId(
        @PathVariable sellerId: Long
    ): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(
            catalogService.findProductsBySellerId(sellerId)
        )
    }

    @PutMapping("/product/decrease-stock")
    fun decreaseStockCount(
        @RequestBody request: DecreaseStockCountRequest,
    ): ResponseEntity<Unit> {
        catalogService.decreaseStockCount(request)
        return ResponseEntity.noContent().build()
    }
}