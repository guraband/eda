package eda.catalog.cassandra.entity

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table
class Product(
    @PrimaryKey
    val id: Long,

    val sellerId: Long,

    val name: String,

    val description: String,

    val price: Long,

    val stockCount: Int,

    val tags: List<String>,
) {
}