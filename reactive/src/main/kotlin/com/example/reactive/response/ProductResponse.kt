package com.example.reactive.response

import com.example.reactive.model.Product

class ProductResponse (
    val id: String,
    val name: String,
    val price: Double,
    val currency: String
) {
    companion object {
        fun fromEntity(product: Product): ProductResponse =
            ProductResponse(
                id = product.id!!,
                name = product.name,
                price = product.price,
                currency = "${product.currency.longName}(${product.currency.relativeValue}$)"
            )
    }
}