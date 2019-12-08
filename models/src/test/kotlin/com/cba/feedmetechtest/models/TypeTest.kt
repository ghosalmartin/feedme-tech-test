package com.cba.feedmetechtest.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class TypeTest {

    @Test
    fun `returns EVENT when calling with event`() {
        assertEquals(Type.EVENT, Type.from(Type.EVENT_TYPE))
    }

    @Test
    fun `returns MARKET when calling with market`() {
        assertEquals(Type.MARKET, Type.from(Type.MARKET_TYPE))
    }

    @Test
    fun `returns OUTCOME_TYPE when calling with outcome`() {
        assertEquals(Type.OUTCOME, Type.from(Type.OUTCOME_TYPE))
    }

    @Test
    fun `returns IllegalArgumentException when random strings given`() {
        assertThrows(IllegalArgumentException::class.java) {
            Type.from(UUID.randomUUID().toString())
        }
    }

}