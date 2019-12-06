package com.cba.feedmetechtest.consumer.messaging

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer {

    companion object {
        private const val TOPIC = "dev.betting"
    }

    @KafkaListener(topics = [TOPIC])
    fun consume(item: String) {
        println(item)
    }
}