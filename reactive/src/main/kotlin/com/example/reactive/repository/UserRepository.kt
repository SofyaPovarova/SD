package com.example.reactive.repository

import com.example.reactive.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository: ReactiveMongoRepository<User, String> {
    fun findByName(name: String): Mono<User>
}