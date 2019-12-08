package com.cba.feedmetechtest.consumer.db.models

import com.cba.feedmetechtest.models.Body
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EventModelTest {

    private val eventId = "eventID"
    private val category = "category"
    private val subCategory = "subCategory"
    private val name = "name"
    private val startTime = "12345678910".toBigInteger()
    private val displayed = true
    private val suspended = false

    private val event = Body.Event(
        eventId,
        category,
        subCategory,
        name,
        startTime,
        displayed,
        suspended
    )

    @Test
    fun `fromBody creates correct EventModel`() {
        //When
        val eventModel = EventModel.fromBody(event)

        //Then
        assertEquals(eventId, eventModel.eventId)
        assertEquals(category, eventModel.category)
        assertEquals(subCategory, eventModel.subCategory)
        assertEquals(name, eventModel.name)
        assertEquals(startTime, eventModel.startTime)
        assertEquals(suspended, eventModel.suspended)
        assertEquals(displayed, eventModel.displayed)
        assertEquals(emptyList<MarketModel>(), eventModel.markets)
    }
}