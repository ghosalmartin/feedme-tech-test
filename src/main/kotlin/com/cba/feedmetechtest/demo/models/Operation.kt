package com.cba.feedmetechtest.demo.models

enum class Operation(val string: String) {
    CREATE("create"),
    UPDATE("update");

    companion object {
        fun from(string: String): Operation =
            values().find { it.string == string } ?: throw IllegalArgumentException()
    }

}