package com.example.reactive.repository

import com.example.reactive.model.Product
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ProductRepository: ReactiveMongoRepository<Product, String> {
    fun findByName(userId: String): Flux<Product>
}