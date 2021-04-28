package com.example.reactive.config

import com.example.reactive.repository.ProductRepository
import com.example.reactive.repository.UserRepository
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(
    basePackageClasses = [UserRepository::class, ProductRepository::class]
)
class MongoConfig : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName() = "productsDB"

    override fun reactiveMongoClient() = mongoClient()

    @Bean
    fun mongoClient(): MongoClient = MongoClients.create()
}