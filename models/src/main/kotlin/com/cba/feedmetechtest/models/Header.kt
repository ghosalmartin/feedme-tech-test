package com.cba.feedmetechtest.models

import java.math.BigInteger

data class Header(
    val msgId: Int,
    val operation: Operation,
    val type: Type,
    val timestamp: BigInteger
) {
    companion object {
        fun fromList(fields: List<String>): Header =
            Header(
                fields[0].toInt(),
                Operation.from(fields[1]),
                Type.from(fields[2]),
                fields[3].toBigInteger()
            )
    }
}