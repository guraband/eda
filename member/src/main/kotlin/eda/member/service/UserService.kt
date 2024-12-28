package eda.member.service

import eda.member.dto.UserDto
import eda.member.dto.UserModifyRequest
import eda.member.dto.UserRequest
import eda.member.entity.User
import eda.member.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun resisterUser(request: UserRequest): UserDto {
        val user = User(
            userId = request.userId,
            userName = request.name,
        )
        userRepository.save(user)
        return UserDto.of(user)
    }

    fun modifyUser(id: String, request: UserModifyRequest): UserDto {
        val user = userRepository.findByUserId(id) ?: throw IllegalArgumentException("User not found")
        user.updateName(request.name)
        userRepository.save(user)
        return UserDto.of(user)
    }

    fun getUser(userId: String): UserDto {
        return userRepository.findByUserId(userId)
            ?.let { UserDto.of(it) }
            ?: throw IllegalArgumentException("User not found")
    }
}