package com.cba.feedmetechtest.consumer.messaging

import com.cba.feedmetechtest.consumer.db.DbWriter
import com.cba.feedmetechtest.models.Message
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val mapper: ObjectMapper,
    private val dbWriter: DbWriter
) {

    companion object {
        private const val TOPIC = "dev.betting"
    }

    @KafkaListener(id = "message", topics = [TOPIC])
    fun consume(item: String) {
        dbWriter.write(mapper.readValue<Message>(item).body)
    }
}