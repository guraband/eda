package eda.catalog.service

import eda.catalog.cassandra.entity.Product
import eda.catalog.cassandra.repository.ProductRepository
import eda.catalog.dto.RegisterProductRequest
import eda.catalog.feign.SearchClient
import eda.catalog.mariadb.entity.SellerProduct
import eda.catalog.mariadb.repository.SellerProductRepository
import eda.common.dto.DecreaseStockCountRequest
import eda.common.dto.Message
import eda.common.dto.ProductResponse
import eda.common.dto.ProductTagRequest
import eda.common.enums.MessageTopic
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CatalogService(
    private val productRepository: ProductRepository,
    private val sellerProductRepository: SellerProductRepository,
    private val searchClient: SearchClient,
    private val jsonKafkaTemplate: KafkaTemplate<String, Message>
) {
    @Transactional
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
            stockCount = request.stockCount,
            tags = request.tags,
        )
        productRepository.save(product)

//        searchClient.addTagCache(ProductTagRequest(product.id, product.tags))

        jsonKafkaTemplate.send(MessageTopic.PRODUCT_TAGS_ADDED.topicName, ProductTagRequest(product.id, product.tags))

        return product.toResponseDto()
    }

    @Transactional
    fun deleteProduct(
        productId: Long,
    ) {
        val product = productRepository.findById(productId)
            .orElseThrow { throw RuntimeException("Product not found") }

        productRepository.deleteById(productId)
        sellerProductRepository.deleteById(productId)

//        searchClient.removeTagCache(ProductTagRequest(product.id, product.tags))

        jsonKafkaTemplate.send(MessageTopic.PRODUCT_TAGS_REMOVED.topicName, ProductTagRequest(product.id, product.tags))
    }

    fun findProductsBySellerId(
        sellerId: Long,
    ): List<ProductResponse> {
        val productIds = sellerProductRepository.findAllBySellerId(sellerId)
            .map { it.id!! }

        return productRepository.findAllByIdIn(productIds)
            .map { it.toResponseDto() }
    }

    fun getProductById(
        productId: Long,
    ): ProductResponse {
        val product = productRepository.findById(productId)
            .orElseThrow { throw RuntimeException("Product not found") }
        return product.toResponseDto()
    }

    @Transactional
    fun decreaseStockCount(
        request: DecreaseStockCountRequest
    ): ProductResponse {
        val product = productRepository.findById(request.productId)
            .orElseThrow { throw RuntimeException("Product not found") }
        product.decreaseStockCount(request.amount)
        productRepository.save(product)

        return product.toResponseDto()
    }
}