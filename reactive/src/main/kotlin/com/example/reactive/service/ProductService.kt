package com.example.reactive.service

import com.example.reactive.model.Currency
import com.example.reactive.model.Product
import com.example.reactive.repository.ProductRepository
import com.example.reactive.repository.UserRepository
import com.example.reactive.request.ProductRequest
import com.example.reactive.utils.NotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductService (
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {
    fun createProduct(request: ProductRequest): Mono<Product> =
        productRepository.save(
            Product(
                name = request.name,
                price = Currency.convert(request.price, Currency.valueOf(request.currency)),
                currency = Currency.USD
        )
        )

    fun findAll(name: String): Mono<MutableList<Product>> =
        userRepository.findByName(name)
            .switchIfEmpty(
                Mono.error(
                    NotFoundException("User with name $name not found")
                )
            ).flatMap { user ->
                productRepository.findAll().map { product ->
                    Product(
                        name = product.name,
                        price = Currency.convert(product.price, user.currency),
                        currency = user.currency)
                }.collectList()
            }

}