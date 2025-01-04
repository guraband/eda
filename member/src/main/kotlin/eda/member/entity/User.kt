package eda.member.entity

import eda.common.dto.UserDto
import jakarta.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val userId: String,

    userName: String,
) {
    @Column
    var name: String = userName
        protected set

    fun updateName(userName: String) {
        this.name = userName
    }

    fun toUserDto() : UserDto {
        return UserDto(
            id = this.id!!,
            userId = this.userId,
            name = this.name,
        )
    }
}