package com.example.reactive.service

import com.example.reactive.model.Currency
import com.example.reactive.model.User
import com.example.reactive.repository.UserRepository
import com.example.reactive.request.UserRequest
import com.example.reactive.utils.NotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun createUser(request: UserRequest): Mono<User> =
        Currency.valueOf(request.currency).let {
            print(it)
            userRepository.save(
                User(
                    name = request.name,
                    currency = it
                )
            )
        }


    fun findAll(): Flux<User> =
        userRepository.findAll()

    fun findByName(name: String): Mono<User> =
        userRepository.findByName(name)
            .switchIfEmpty(
                Mono.error(
                    NotFoundException("User with name $name not found")
                )
            )
}