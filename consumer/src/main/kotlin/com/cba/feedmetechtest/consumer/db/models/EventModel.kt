package com.cba.feedmetechtest.consumer.db.models

import com.cba.feedmetechtest.models.Body
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigInteger

@Document("event")
data class EventModel(
    @Id val eventId: String,
    val category: String,
    val subCategory: String,
    val name: String,
    val startTime: BigInteger,
    val suspended: Boolean,
    val displayed: Boolean,
    val markets: List<MarketModel> = emptyList()
) {

    companion object {
        fun fromBody(body: Body.Event) =
            EventModel(
                eventId = body.eventId,
                category = body.category,
                subCategory = body.subCategory,
                name = body.name,
                startTime = body.startTime,
                suspended = body.suspended,
                displayed = body.displayed
            )
    }

}