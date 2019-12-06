package com.cba.feedmetechtest.demo.models

import java.math.BigInteger

data class Header(
    val msgId: Int,
    val operation: Operation,
    val type: String,
    val timestamp: BigInteger
){
    companion object {
        fun fromList(fields: List<String>): Header =
            Header(
                fields[0].toInt(),
                Operation.from(fields[1]),
                fields[2],
                fields[3].toBigInteger()
            )
    }
}