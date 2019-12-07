package com.cba.feedmetechtest.consumer

import com.cba.feedmetechtest.consumer.messaging.Consumer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.kafka.annotation.EnableKafka


@EnableKafka
@EnableMongoRepositories
@SpringBootApplication
class ConsumerApplication

private val consumer = Consumer()

fun main(args: Array<String>) {
    runApplication<ConsumerApplication>(*args)
}
