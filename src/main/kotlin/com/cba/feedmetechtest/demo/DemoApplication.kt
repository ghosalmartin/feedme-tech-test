package com.cba.feedmetechtest.demo

import com.cba.feedmetechtest.demo.messaging.Consumer
import com.cba.feedmetechtest.demo.messaging.Producer
import com.cba.feedmetechtest.demo.models.toMessage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

private val consumer = Consumer()
private val producer = Producer()

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
    consumer.consume {
        producer.produce(it.toMessage())
    }
}
