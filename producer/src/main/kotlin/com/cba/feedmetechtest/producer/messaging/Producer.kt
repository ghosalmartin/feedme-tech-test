package com.cba.feedmetechtest.producer.messaging

import com.cba.feedmetechtest.models.Message
import org.springframework.kafka.core.KafkaTemplate

class Producer(private val kafkaTemplate: KafkaTemplate<String, Message>) {

    companion object {
        private const val TOPIC = "dev.betting"
    }

    fun produce(message: Message) {
        kafkaTemplate.send(TOPIC, message)
    }
}