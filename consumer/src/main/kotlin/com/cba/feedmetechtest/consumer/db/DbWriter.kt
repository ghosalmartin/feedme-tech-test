package com.cba.feedmetechtest.consumer.db

import com.cba.feedmetechtest.consumer.db.models.EventModel
import com.cba.feedmetechtest.consumer.db.models.MarketModel
import com.cba.feedmetechtest.consumer.db.models.OutcomeModel
import com.cba.feedmetechtest.models.Body
import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo

class DbWriter(
    private val mongoOps: MongoOperations = MongoTemplate(MongoClients.create(), "database"),
    private val findEventByMarketIdQuery: (String) -> Query = { id ->
        Query(
            Criteria.where("markets").elemMatch(
                Criteria.where(
                    "id"
                ).isEqualTo(id)
            )
        )
    }
) {

    fun write(body: Body) = when (body) {
        is Body.Event -> saveEvent(body)
        is Body.Market -> saveMarket(body)
        is Body.Outcome -> saveOutcome(body)
    }

    private fun saveEvent(body: Body.Event) = mongoOps.save(EventModel.fromBody(body))

    private fun saveMarket(body: Body.Market) = mongoOps.findById(body.eventId, EventModel::class.java)?.let {
        mongoOps.save(it.copy(markets = it.markets.plus(MarketModel.fromBody(body))))
    }

    private fun saveOutcome(body: Body.Outcome) =
        mongoOps.find(
            findEventByMarketIdQuery.invoke(body.marketId),
            EventModel::class.java
        ).firstOrNull()?.upsertOutcome(body)?.let {
            mongoOps.save(it)
        }
}

fun EventModel.upsertOutcome(outcome: Body.Outcome) =
    markets.find { it.id == outcome.marketId }?.let { market ->
        copy(
            markets = markets
                //Delete old market incase it already exists
                .filterNot { it.id == market.id }
                //Add new market to existing markets
                .plus(market.upsertOutcome(outcome))
        )
    }

fun MarketModel.upsertOutcome(outcome: Body.Outcome) =
    copy(
        outcomes = outcomes
            //Delete old outcome incase it exists
            .filterNot { it.id == outcome.outcomeId }
            //Add new outcome to existing outcomes
            .plus(OutcomeModel.fromBody(outcome))
    )