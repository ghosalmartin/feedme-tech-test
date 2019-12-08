package com.cba.feedmetechtest.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HeaderTest {

    @Test
    fun `returns Header object when calling fromList with appropriate strings list`(){
        //Given
        val msgId = "123"
        val operation = "create"
        val type = "event"
        val timeStamp = "12345678910"
        val fields = listOf(msgId, operation, type, timeStamp)

        //When
        val header = Header.fromList(fields)

        //Then
        assertEquals(msgId, header.msgId.toString())
        assertEquals(operation, header.operation.string)
        assertEquals(type, header.type.type)
        assertEquals(msgId, header.msgId.toString())
    }

}