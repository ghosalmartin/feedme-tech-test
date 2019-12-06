package com.cba.feedmetechtest.demo.messaging

import com.cba.feedmetechtest.demo.models.Message
import org.springframework.kafka.core.KafkaTemplate

class Producer(private val kafkaTemplate: KafkaTemplate<String, Message>) {

    companion object {
        private const val TOPIC = "dev.betting"
    }

    fun produce(message: Message) {
        kafkaTemplate.send(TOPIC, message)
    }
}