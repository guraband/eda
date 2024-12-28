package eda.member.dto

import eda.member.entity.User

data class UserDto(
    val id: Long,
    val userId: String,
    val name: String,
) {
    companion object {
        fun of(user: User): UserDto {
            return UserDto(
                id = user.id!!,
                userId = user.userId,
                name = user.name,
            )
        }
    }
}

data class UserRequest(
    val userId: String,
    val name: String,
)

data class UserModifyRequest(
    val name: String,
)