package com.cba.feedmetechtest.models

import java.math.BigDecimal

sealed class Body(
    open val eventId: String,
    open val name: String,
    open val displayed: Boolean,
    open val suspended: Boolean
) {

    companion object {
        private const val MARKET_TYPE = "market"
        private const val OUTCOME_TYPE = "outcome"
        private const val EVENT_TYPE = "event"

        fun fromList(type: String, fields: List<String>): Body =
            when (type) {
                MARKET_TYPE -> Market.fromList(fields)
                OUTCOME_TYPE -> Outcome.fromList(fields)
                EVENT_TYPE -> Event.fromList(fields)
                else -> throw IllegalArgumentException("unknown type")
            }
    }

    data class Event(
        override val eventId: String,
        val category: String,
        val subCategory: String,
        override val name: String,
        val startTime: BigDecimal,
        override val displayed: Boolean,
        override val suspended: Boolean
    ) : Body(eventId, name, displayed, suspended) {
        companion object {
            fun fromList(fields: List<String>): Body =
                Event(
                    fields[0],
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4].toBigDecimal(),
                    fields[5].to01Boolean(),
                    fields[6].to01Boolean()
                )
        }

    }

    data class Market(
        override val eventId: String,
        val marketId: String,
        override val name: String,
        override val displayed: Boolean,
        override val suspended: Boolean
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
        override val eventId: String,
        val outcomeId: String,
        override val name: String,
        val price: String,
        override val displayed: Boolean,
        override val suspended: Boolean
    ) : Body(eventId, name, displayed, suspended) {
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