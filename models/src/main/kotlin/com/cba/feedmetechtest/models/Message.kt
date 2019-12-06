package com.cba.feedmetechtest.models

data class Message(
    val header: Header,
    val body: Body
)

fun String.toMessage(): Message {
    val delimiter = "|"
    val fields = this
        .removePrefix(delimiter)
        .removeSuffix(delimiter)
        .split("(?<!\\\\)\\$delimiter".toRegex())
        .map { it.replace("\\", "") }

    check(fields.size > 3) { "Incomplete header" }

    val header = Header.fromList(fields.subList(0, 4))
    val message = Body.fromList(header.type, fields.subList(4, fields.size))

    return Message(header, message)
}