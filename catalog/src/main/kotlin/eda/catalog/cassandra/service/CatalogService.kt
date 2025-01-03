package eda.catalog.cassandra.service

import eda.catalog.cassandra.dto.DecreaseStockCountRequest
import eda.catalog.cassandra.dto.ProductResponse
import eda.catalog.cassandra.dto.RegisterProductRequest
import eda.catalog.cassandra.entity.Product
import eda.catalog.cassandra.repository.ProductRepository
import eda.catalog.mariadb.entity.SellerProduct
import eda.catalog.mariadb.repository.SellerProductRepository
import org.springframework.stereotype.Service

@Service
class CatalogService(
    private val productRepository: ProductRepository,
    private val sellerProductRepository: SellerProductRepository,
) {
    fun registerProduct(
        request: RegisterProductRequest,
    ): ProductResponse {
        val sellerProduct = SellerProduct(
            sellerId = request.sellerId,
        )
        sellerProductRepository.save(sellerProduct)

        val product = Product(
            id = sellerProduct.id!!,
            sellerId = request.sellerId,
            name = request.name,
            description = request.description,
            price = request.price,
            _stockCount = request.stockCount,
            tags = request.tags,
        )
        productRepository.save(product)

        return ProductResponse(product)
    }

    fun deleteProduct(
        productId: Long,
    ) {
        productRepository.deleteById(productId)
        sellerProductRepository.deleteById(productId)
    }

    fun findProductsBySellerId(
        sellerId: Long,
    ): List<ProductResponse> {
        val productIds = sellerProductRepository.findAllBySellerId(sellerId)
            .map { it.id!! }

        return productRepository.findAllByIdIn(productIds)
            .map { ProductResponse(it) }
    }

    fun getProductById(
        productId: Long,
    ): ProductResponse {
        val product = productRepository.findById(productId)
            .orElseThrow { throw RuntimeException("Product not found") }
        return ProductResponse(product)
    }

    fun decreaseStockCount(
        request: DecreaseStockCountRequest
    ) : ProductResponse {
        val product = productRepository.findById(request.productId)
            .orElseThrow { throw RuntimeException("Product not found") }
        product.decreaseStockCount(request.amount)
        productRepository.save(product)

        return ProductResponse(product)
    }
}