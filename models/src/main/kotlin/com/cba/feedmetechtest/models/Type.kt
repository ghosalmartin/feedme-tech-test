package com.cba.feedmetechtest.models

enum class Type(val type: String) {
    EVENT("event"),
    MARKET("market"),
    OUTCOME("outcome");

    companion object {
        const val EVENT_TYPE = "event"
        const val MARKET_TYPE = "market"
        const val OUTCOME_TYPE = "outcome"

        fun from(type: String) =
            values().find { it.type == type } ?: throw IllegalArgumentException()
    }
}