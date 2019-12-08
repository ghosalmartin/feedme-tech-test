package com.cba.feedmetechtest.consumer

import com.cba.feedmetechtest.consumer.db.DbWriter
import com.cba.feedmetechtest.consumer.messaging.Consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@EnableMongoRepositories
@SpringBootApplication
class ConsumerApplication

@Configuration
class ConsumerConfig {
    @Bean
    fun mapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Bean
    fun dbConnector(): DbWriter = DbWriter()
}

@Autowired
private lateinit var consumer: Consumer

fun main(args: Array<String>) {
    runApplication<ConsumerApplication>(*args)
}
