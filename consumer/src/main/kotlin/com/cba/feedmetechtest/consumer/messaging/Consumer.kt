package com.cba.feedmetechtest.consumer.messaging

import com.cba.feedmetechtest.consumer.db.models.EventModel
import com.cba.feedmetechtest.consumer.db.models.MarketModel
import com.cba.feedmetechtest.consumer.db.models.OutcomeModel
import com.cba.feedmetechtest.models.Body
import com.cba.feedmetechtest.models.Header
import com.cba.feedmetechtest.models.Message
import com.cba.feedmetechtest.models.Operation
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule()),
    private val mongoOps: MongoOperations = MongoTemplate(MongoClients.create(), "database")
) {

    companion object {
        private const val TOPIC = "dev.betting"
    }

    @KafkaListener(id = "message", topics = [TOPIC])
    fun consume(item: String) {
        when (val body = mapper.readValue<Message>(item).body) {
            is Body.Event -> mongoOps.save(EventModel.fromBody(body))
            is Body.Market ->
                mongoOps.findById(body.eventId, EventModel::class.java)?.let {
                    mongoOps.save(it.copy(markets = it.markets.plus(MarketModel.fromBody(body))))
                }
            is Body.Outcome ->
                mongoOps.find(
                    Query().apply { addCriteria(Criteria.where("markets").elemMatch(Criteria.where("id").isEqualTo(body.marketId))) },
                    MarketModel::class.java
                ).firstOrNull {
                    print(it)
                    it.id == body.marketId
                }?.let {
                    mongoOps.save(it.copy(outcomes = it.outcomes.plus(OutcomeModel.fromBody(body))))
                }
        }
    }
}