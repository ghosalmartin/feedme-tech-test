package com.cba.feedmetechtest.consumer.db.models

import com.cba.feedmetechtest.models.Body
import org.springframework.data.annotation.Id

data class OutcomeModel(
    @Id val id: String,
    val name: String,
    val price: String,
    val suspended: Boolean,
    val displayed: Boolean
) {
    companion object {
        fun fromBody(outcome: Body.Outcome) =
            OutcomeModel(
                outcome.outcomeId,
                outcome.name,
                outcome.price,
                outcome.suspended,
                outcome.displayed
            )
    }
}