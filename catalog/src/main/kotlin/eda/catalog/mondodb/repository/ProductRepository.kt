package eda.catalog.mondodb.repository

import eda.catalog.mondodb.entity.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository : MongoRepository<Product, Long> {
    fun findAllByIdIn(ids: List<Long>): List<Product>
}