package eda.common.dto

data class AddressRequest(
    val userId: Long,
    val address: String,
    val alias: String,
)

data class AddressResponse(
    val id: Long,
    val userId: Long,
    val address: String,
    val alias: String,
)