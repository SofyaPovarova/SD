package com.example.reactive.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

data class Product(
    @Id
    val id: String? = null,
    val name: String,
    val price: Double,
    val currency: Currency
)

data class User(
    @Id
    val id: String? = null,
    val name: String,
    val currency: Currency
    )