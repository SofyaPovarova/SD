package com.example.reactive.controller

import com.example.reactive.response.ProductResponse.Companion.fromEntity
import com.example.reactive.model.Product
import com.example.reactive.request.ProductRequest
import com.example.reactive.response.ProductResponse
import com.example.reactive.service.ProductService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
    ) {
    @PostMapping("/add")
    fun createProduct(@RequestBody request: ProductRequest): Mono<ProductResponse> =
        productService.createProduct(request)
            .map { fromEntity(it) }

    @GetMapping("/all/{name}")
    fun getAllProducts(@PathVariable name: String): Mono<MutableList<Product>> =
        productService.findAll(name)

}