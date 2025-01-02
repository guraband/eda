package eda.catalog.cassandra.repository

import eda.catalog.cassandra.entity.Product
import org.springframework.data.cassandra.repository.CassandraRepository

interface ProductRepository : CassandraRepository<Product, Long> {
}