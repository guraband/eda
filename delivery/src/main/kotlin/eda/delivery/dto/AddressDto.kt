package eda.delivery.dto

import eda.delivery.entity.Address

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
) {
    constructor(address: Address) : this(
        id = address.id!!,
        userId = address.userId,
        address = address.address,
        alias = address.alias,
    )
}