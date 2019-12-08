package com.cba.feedmetechtest.consumer.db.models

import com.cba.feedmetechtest.models.Body
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MarketModelTest {

    private val eventId = "eventID"
    private val marketId = "marketID"
    private val name = "name"
    private val displayed = true
    private val suspended = false

    private val market = Body.Market(
        eventId,
        marketId,
        name,
        displayed,
        suspended
    )

    @Test
    fun `fromBody creates correct MarketModel`() {
        //When
        val marketModel = MarketModel.fromBody(market)

        //Then
        assertEquals(marketId, marketModel.id)
        assertEquals(name, marketModel.name)
        assertEquals(suspended, marketModel.suspended)
        assertEquals(displayed, marketModel.displayed)
        assertEquals(emptyList<OutcomeModel>(), marketModel.outcomes)
    }

}