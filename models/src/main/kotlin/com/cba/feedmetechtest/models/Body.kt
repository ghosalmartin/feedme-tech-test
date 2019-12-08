package com.cba.feedmetechtest.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigInteger


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(
    JsonSubTypes.Type(Body.Event::class, name = Type.EVENT_TYPE),
    JsonSubTypes.Type(Body.Market::class, name = Type.MARKET_TYPE),
    JsonSubTypes.Type(Body.Outcome::class, name = Type.OUTCOME_TYPE)
)
sealed class Body(
    open val parentId: String,
    open val name: String,
    open val displayed: Boolean,
    open val suspended: Boolean
) {

    companion object {
        fun fromList(type: Type, fields: List<String>): Body =
            when (type) {
                Type.EVENT -> Event.fromList(fields)
                Type.MARKET -> Market.fromList(fields)
                Type.OUTCOME -> Outcome.fromList(fields)
            }
    }

    data class Event(
        val eventId: String,
        val category: String,
        val subCategory: String,
        override val name: String,
        val startTime: BigInteger,
        override val displayed: Boolean,
        override val suspended: Boolean,
        @JsonProperty("@type") val type: String = Type.EVENT.type
    ) : Body(eventId, name, displayed, suspended) {
        companion object {
            fun fromList(fields: List<String>): Body =
                Event(
                    fields[0],
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4].toBigInteger(),
                    fields[5].to01Boolean(),
                    fields[6].to01Boolean()
                )
        }

    }

    data class Market(
        val eventId: String,
        val marketId: String,
        override val name: String,
        override val displayed: Boolean,
        override val suspended: Boolean,
        @JsonProperty("@type") val type: String = Type.MARKET.type
    ) : Body(eventId, name, displayed, suspended) {
        companion object {
            fun fromList(fields: List<String>): Body =
                Market(
                    fields[0],
                    fields[1],
                    fields[2],
                    fields[3].to01Boolean(),
                    fields[4].to01Boolean()
                )
        }
    }

    data class Outcome(
        val marketId: String,
        val outcomeId: String,
        override val name: String,
        val price: String,
        override val displayed: Boolean,
        override val suspended: Boolean,
        @JsonProperty("@type") val type: String = Type.OUTCOME.type
    ) : Body(marketId, name, displayed, suspended) {
        companion object {
            fun fromList(fields: List<String>): Body =
                Outcome(
                    fields[0],
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4].to01Boolean(),
                    fields[5].to01Boolean()
                )
        }
    }
}

fun String.to01Boolean() =
    when (this) {
        "0" -> false
        "1" -> true
        else -> throw IllegalArgumentException("Not a quantum computer")
    }