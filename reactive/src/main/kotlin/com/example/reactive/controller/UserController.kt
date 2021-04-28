package com.example.reactive.controller

import com.example.reactive.response.UserResponse.Companion.fromEntity
import com.example.reactive.request.UserRequest
import com.example.reactive.response.UserResponse
import com.example.reactive.service.UserService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
    ) {
    @PostMapping("/add")
    fun createUser(@RequestBody request: UserRequest): Mono<UserResponse> =
        userService.createUser(request)
            .map { fromEntity(it) }

    @GetMapping("/all")
    fun findAllUsers(): Flux<UserResponse> {
        return userService.findAll()
            .map { fromEntity(it)}
    }

}
