package com.cba.feedmetechtest.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*


class BodyTest {

    @Test
    fun `returns Event when type is event`() {
        //Given
        val eventId = "eventID"
        val category = "category"
        val subCategory = "subCategory"
        val name = "name"
        val startTime = "12345678910"
        val displayed = "0"
        val suspended = "1"
        val fields = listOf(eventId, category, subCategory, name, startTime, displayed, suspended)

        //When
        val body = Body.fromList(
            Type.EVENT,
            fields
        )
        val event = (body as Body.Event)

        //Then
        assertEquals(eventId, event.eventId)
        assertEquals(category, event.category)
        assertEquals(subCategory, event.subCategory)
        assertEquals(name, event.name)
        assertEquals(startTime, event.startTime.toString())
        assertFalse(event.displayed)
        assertTrue(event.suspended)
        assertEquals(Type.EVENT.type, event.type)
    }

    @Test
    fun `returns Market when type is market`() {
        //Given
        val eventId = "eventID"
        val marketId = "marketID"
        val name = "name"
        val displayed = "0"
        val suspended = "1"
        val fields = listOf(eventId, marketId, name, displayed, suspended)

        //When
        val body = Body.fromList(
            Type.MARKET,
            fields
        )
        val event = (body as Body.Market)

        //Then
        assertEquals(eventId, event.eventId)
        assertEquals(marketId, event.marketId)
        assertEquals(name, event.name)
        assertFalse(event.displayed)
        assertTrue(event.suspended)
        assertEquals(Type.MARKET.type, event.type)
    }

    @Test
    fun `returns Outcome when type is outcome`() {
        //Given
        val marketId = "marketID"
        val outcomeId = "outcomeID"
        val name = "name"
        val price = "12"
        val displayed = "0"
        val suspended = "1"
        val fields = listOf(marketId, outcomeId, name, price, displayed, suspended)

        //When
        val body = Body.fromList(
            Type.OUTCOME,
            fields
        )
        val event = (body as Body.Outcome)

        //Then
        assertEquals(marketId, event.marketId)
        assertEquals(outcomeId, event.outcomeId)
        assertEquals(name, event.name)
        assertEquals(price, event.price)
        assertFalse(event.displayed)
        assertTrue(event.suspended)
        assertEquals(Type.OUTCOME.type, event.type)
    }

    @Test
    fun `to01Boolean returns true when string is 0`() {
        assertFalse("0".to01Boolean())
    }

    @Test
    fun `to01Boolean returns true when string is 1`() {
        assertTrue("1".to01Boolean())
    }

    @Test
    fun `to01Boolean returns IllegalArgumentException when string does not conform to list`() {
        assertThrows(IllegalArgumentException::class.java) {
            UUID.randomUUID().toString().to01Boolean()
        }
    }
}