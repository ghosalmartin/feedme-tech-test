package com.cba.feedmetechtest.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

class OperationTest {

    @Test
    fun `returns CREATE when create is passed`() {
        assertEquals(Operation.CREATE, Operation.from("create"))
    }

    @Test
    fun `returns UPDATE when update is passed`() {
        assertEquals(Operation.UPDATE, Operation.from("update"))
    }

    @Test
    fun `throws IllegalArgumentException when random string given`() {
        assertThrows(IllegalArgumentException::class.java) {
            Operation.from(UUID.randomUUID().toString())
        }
    }

}