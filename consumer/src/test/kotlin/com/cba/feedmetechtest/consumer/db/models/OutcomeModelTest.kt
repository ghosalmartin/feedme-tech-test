package com.cba.feedmetechtest.consumer.db.models

import com.cba.feedmetechtest.models.Body
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OutcomeModelTest {

    private val eventId = "eventID"
    private val marketId = "marketID"
    private val name = "name"
    private val price = "10/1"
    private val displayed = true
    private val suspended = false

    private val outcome = Body.Outcome(
        eventId,
        marketId,
        name,
        price,
        displayed,
        suspended
    )

    @Test
    fun `fromBody creates correct OutcomeModel`() {
        //When
        val outcomeModel = OutcomeModel.fromBody(outcome)

        //Then
        assertEquals(marketId, outcomeModel.id)
        assertEquals(name, outcomeModel.name)
        assertEquals(price, outcomeModel.price)
        assertEquals(suspended, outcomeModel.suspended)
        assertEquals(displayed, outcomeModel.displayed)
    }

}