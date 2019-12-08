package com.cba.feedmetechtest.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException

class MessageTest {

    @Test
    fun `invalid message returns IllegalStateException`() {
        assertThrows(IllegalStateException::class.java) {
            "|test|test|test".toMessage()
        }
    }

    @Test
    fun `returns valid message when complete message sent`() {
        //Given
        val msgId = "123"
        val operation = "create"
        val type = "event"
        val timeStamp = "12345678910"
        val eventId = "eventID"
        val category = "category"
        val subCategory = "subCategory"
        val namePartOne = "nameOne"
        val namePartTwo = "nameTwo"
        val startTime = "12345678910"
        val displayed = "0"
        val suspended = "1"

        val stringMessage =
            "|$msgId|$operation|$type|$timeStamp|$eventId|$category|$subCategory|\\\\|$namePartOne\\\\|$namePartTwo\\\\||$startTime|$displayed|$suspended|"

        //When
        val message = stringMessage.toMessage()
        val event = (message.body as Body.Event)

        //Then
        assertEquals(msgId, message.header.msgId.toString())
        assertEquals(Operation.CREATE, message.header.operation)
        assertEquals(Type.EVENT, message.header.type)
        assertEquals(timeStamp, message.header.timestamp.toString())
        assertEquals(eventId, event.eventId)
        assertEquals(category, event.category)
        assertEquals(subCategory, event.subCategory)
        assertEquals("|$namePartOne|$namePartTwo|", event.name)
        assertEquals(startTime, event.startTime.toString())
        assertFalse(event.displayed)
        assertTrue(event.suspended)
    }
}