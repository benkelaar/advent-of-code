package com.bartenkelaar.year2015.electronics

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CircuitryConnectionTest {
    @Test
    fun `Verify input connection`() {
        val connection = CircuitryConnection.parseFrom("254 -> out")

        val actual = connection.calculateCurrent(emptyMap())

        assertEquals(254.toUShort(), actual.second)
        assertEquals("out", actual.first)
    }

    @Test
    fun `Verify connection`() {
        val connection = CircuitryConnection.parseFrom("in -> out")

        val actual = connection.calculateCurrent(mapOf("in" to 636.toUShort()))

        assertEquals(636.toUShort(), actual.second)
        assertEquals("out", actual.first)
    }

    @Test
    fun `Verify not gate`() {
        val connection = CircuitryConnection.parseFrom("NOT in -> out")

        val actual = connection.calculateCurrent(mapOf("in" to (UShort.MAX_VALUE - 4.toUInt()).toUShort()))

        assertEquals(4.toUShort(), actual.second)
        assertEquals("out", actual.first)
    }

    @Test
    fun `Verify and gate`() {
        val connection = CircuitryConnection.parseFrom("a AND b -> out")

        val actual = connection.calculateCurrent(mapOf("a" to 5.toUShort(), "b" to 6.toUShort()))

        assertEquals(4.toUShort(), actual.second)
        assertEquals("out", actual.first)
    }

    @Test
    fun `Verify lshift gate`() {
        val connection = CircuitryConnection.parseFrom("a LSHIFT 3 -> out")

        val actual = connection.calculateCurrent(mapOf("a" to 3.toUShort()))

        assertEquals(24.toUShort(), actual.second)
        assertEquals("out", actual.first)
    }

    @Test
    fun `Verify rshift gate`() {
        val connection = CircuitryConnection.parseFrom("a RSHIFT 2 -> out")

        val actual = connection.calculateCurrent(mapOf("a" to 1.toUShort()))

        assertEquals(0.toUShort(), actual.second)
        assertEquals("out", actual.first)
    }
}