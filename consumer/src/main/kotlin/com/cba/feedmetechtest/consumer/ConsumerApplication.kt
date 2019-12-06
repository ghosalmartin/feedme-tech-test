package com.cba.feedmetechtest.consumer

import com.cba.feedmetechtest.consumer.messaging.Consumer
import com.cba.feedmetechtest.models.Message
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer


@EnableKafka
@SpringBootApplication
class ConsumerApplication

private val kafkaTemplate: KafkaTemplate<String, Message> = KafkaTemplate(
    DefaultKafkaProducerFactory(
        mutableMapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "192.168.0.18:9092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
    )
)

private val consumer = Consumer()

fun main(args: Array<String>) {
    runApplication<ConsumerApplication>(*args)
}
