package com.cba.feedmetechtest.consumer.db.models

import com.cba.feedmetechtest.models.Body
import org.springframework.data.annotation.Id

data class MarketModel(
    @Id val id: String,
    val name: String,
    val suspended: Boolean,
    val displayed: Boolean,
    val outcomes: List<OutcomeModel> = emptyList()
) {
    companion object {
        fun fromBody(market: Body.Market) =
            MarketModel(
                id = market.marketId,
                name = market.name,
                suspended = market.suspended,
                displayed = market.displayed
            )
    }
}