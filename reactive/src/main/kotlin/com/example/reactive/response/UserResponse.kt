package com.example.reactive.response

import com.example.reactive.model.User

class UserResponse (
    val id: String,
    val name: String,
    val currency: String
        ) {
    companion object {
        fun fromEntity(user: User): UserResponse =
            UserResponse(
                id = user.id!!,
                name = user.name,
                currency = "${user.currency.longName}(${user.currency.relativeValue}$)"
            )
    }
}