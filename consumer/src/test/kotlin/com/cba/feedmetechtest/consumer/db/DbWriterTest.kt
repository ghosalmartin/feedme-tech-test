package com.cba.feedmetechtest.consumer.db

import com.cba.feedmetechtest.consumer.db.models.EventModel
import com.cba.feedmetechtest.consumer.db.models.MarketModel
import com.cba.feedmetechtest.consumer.db.models.OutcomeModel
import com.cba.feedmetechtest.models.Body
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query

class DbWriterTest {

    private val mockMongoOps = mock<MongoOperations>()
    private val mockFindEventByMarketIdQuery = mock<(String) -> Query>()

    private val dbWriter = DbWriter(mockMongoOps, mockFindEventByMarketIdQuery)

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

    private val eventModel = EventModel.fromBody(event)

    private val marketId = "marketID"

    private val market = Body.Market(
        eventId,
        marketId,
        name,
        displayed,
        suspended
    )

    private val marketModel = MarketModel.fromBody(market)

    private val eventModelWithMarketModel = eventModel.copy(markets = listOf(marketModel))

    private val outcomeId = "outcomeID"
    private val price = "price"

    private val outcome = Body.Outcome(
        marketId,
        outcomeId,
        name,
        price,
        displayed,
        suspended
    )

    private val outcomeModel = OutcomeModel.fromBody(outcome)

    private val eventModelWithMarketModelAndOutcomeModel =
        eventModel.copy(
            markets = listOf(
                marketModel.copy(
                    outcomes = listOf(outcomeModel)
                )
            )
        )


    @Test
    fun `write Event saves as EventModel`() {
        //When
        dbWriter.write(event)

        //Then
        verify(mockMongoOps).save(eventModel)
    }

    @Test
    fun `write Market saves as MarketModel in EventModel`() {
        //Given
        whenever(mockMongoOps.findById(eventId, EventModel::class.java))
            .thenReturn(eventModel)

        //When
        dbWriter.write(market)

        //Then
        verify(mockMongoOps).findById(eventId, EventModel::class.java)
        verify(mockMongoOps).save(eventModelWithMarketModel)
    }

    @Test
    fun `write Outcome saves as OutcomeModel in MarketModel in EventModel`() {
        //Given
        whenever(mockFindEventByMarketIdQuery.invoke(marketId)).thenReturn(Query())
        whenever(mockMongoOps.find(mockFindEventByMarketIdQuery.invoke(marketId), EventModel::class.java))
            .thenReturn(listOf(eventModelWithMarketModel))

        //When
        dbWriter.write(outcome)

        //Then
        verify(mockMongoOps).save(eventModelWithMarketModelAndOutcomeModel)
    }

    @Test
    fun `upsertMarket adds new market when marketId isn't found`() {
        //When
        val eventModelWithNewMarket = eventModel.upsertMarket(market)

        //Then
        assertEquals(marketModel, eventModelWithNewMarket.markets.first())
    }

    @Test
    fun `upsertMarket replaces existing market when marketId is found`() {
        //Given
        val updatedName = "updated_name"
        val updatedMarket = market.copy(name = updatedName)

        //When
        val eventWithUpdatedMarket = eventModelWithMarketModel.upsertMarket(updatedMarket)

        //Then
        assertEquals(1, eventWithUpdatedMarket.markets.size)
        assertEquals(MarketModel.fromBody(updatedMarket), eventWithUpdatedMarket.markets.first())
    }

    @Test
    fun `upsertOutcome adds new outcome to Event Model`() {
        //When
        val eventWithUpdatedMarketAndOutcomes = eventModelWithMarketModel.upsertOutcome(outcome)

        //Then
        assertEquals(outcomeModel, eventWithUpdatedMarketAndOutcomes?.markets?.first()?.outcomes?.first())
    }

    @Test
    fun `upsertOutcome replaces existing outcome to Event Model`() {
        //Given
        val updatedPrice = "100/1"
        val updatedOutcome = outcome.copy(price = updatedPrice)

        //When
        val eventWithUpdatedOutcome = eventModelWithMarketModelAndOutcomeModel.upsertOutcome(updatedOutcome)

        //Then
        assertEquals(1, eventWithUpdatedOutcome?.markets?.first()?.outcomes?.size)
        assertEquals(
            OutcomeModel.fromBody(updatedOutcome),
            eventWithUpdatedOutcome?.markets?.first()?.outcomes?.first()
        )
    }

    @Test
    fun `upsertOutcome adds new outcome to Market Model`() {
        //When
        val updatedMarketModel = marketModel.upsertOutcome(outcome)

        //Then
        assertEquals(outcomeModel, updatedMarketModel.outcomes.first())

    }

    @Test
    fun `upsertOutcome replaces existing outcome in Market Model`() {
        //Given
        val updatedPrice = "100/1"
        val updatedOutcome = outcome.copy(price = updatedPrice)

        //When
        val evenWithUpdatedOutcome = marketModel.copy(outcomes = listOf(outcomeModel)).upsertOutcome(updatedOutcome)

        //Then
        assertEquals(1, evenWithUpdatedOutcome.outcomes.size)
        assertEquals(
            OutcomeModel.fromBody(updatedOutcome),
            evenWithUpdatedOutcome.outcomes.first()
        )
    }

}