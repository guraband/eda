package eda.member.dto

data class UserRequest(
    val userId: String,
    val name: String,
)

data class UserModifyRequest(
    val name: String,
)