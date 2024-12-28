package eda.member.repository

import eda.member.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>{
    fun findByUserId(userId: String): User?
}