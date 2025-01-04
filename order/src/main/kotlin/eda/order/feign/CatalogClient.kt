package eda.order.feign

import eda.common.dto.DecreaseStockCountRequest
import eda.common.dto.ProductResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping


@FeignClient(name = "searchClient", url = "http://localhost:8085/v1/catalog")
interface CatalogClient {
    @GetMapping("/product/{productId}")
    fun getProductById(@PathVariable productId: Long): ProductResponse

    @PutMapping("/product/decrease-stock")
    fun decreaseStockCount(request: DecreaseStockCountRequest)
}