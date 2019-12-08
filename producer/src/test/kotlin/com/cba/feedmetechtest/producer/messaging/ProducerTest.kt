package com.cba.feedmetechtest.producer.messaging

import com.cba.feedmetechtest.models.Body
import com.cba.feedmetechtest.models.Message
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.springframework.kafka.core.KafkaTemplate

class ProducerTest {

    private val mockKafkaTemplate = mock<KafkaTemplate<String, Message>>()
    private val producer = Producer(mockKafkaTemplate)

    private val mockParentId = "parent_id"
    private val mockBody = mock<Body>().apply {
        whenever(parentId).thenReturn(mockParentId)
    }
    private val mockMessage = mock<Message>().apply {
        whenever(body).thenReturn(mockBody)
    }

    @Test
    fun `produce calls kafka template with parentId and message`() {
        //When
        producer.produce(mockMessage)

        //Then
        verify(mockKafkaTemplate).send(Producer.TOPIC, mockParentId, mockMessage)
    }

}