package eda.order.dto

data class AddressResponse(
    val id: Long,
    val userId: Long,
    val address: String,
    val alias: String,
)