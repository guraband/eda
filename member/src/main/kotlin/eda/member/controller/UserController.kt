package eda.member.controller

import eda.common.dto.UserDto
import eda.member.dto.UserModifyRequest
import eda.member.dto.UserRequest
import eda.member.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/member/user")
class UserController(
    private val userService: UserService,
) {
    @PostMapping("")
    fun registration(
        @RequestBody request: UserRequest
    ) : UserDto {
        return userService.resisterUser(request)
    }

    @PutMapping("/{userId}")
    fun modify(
        @PathVariable userId: String,
        @RequestBody request: UserModifyRequest
    ) : UserDto {
        return userService.modifyUser(userId, request)
    }

    // 로그인 처리는 단순하게 구현
    @GetMapping("/{userId}")
    fun login(
        @PathVariable userId: String
    ) : UserDto {
        return userService.getUser(userId)
    }
}