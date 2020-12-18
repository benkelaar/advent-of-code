package com.bartenkelaar.year2020.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class DockCommunicatorTest {
    private val communicator = DockCommunicator()

    @Test
    fun example() {
        val input = listOf(
            "mask = 000000000000000000000000000001XXXX0X",
            "mem[8] = 11",
            "mem[7] = 101",
            "mem[8] = 0",
        )

        assertEquals(165L, communicator.solve(input).first)
    }

    @Test
    fun example2() {
        val input = listOf(
            "mask = 000000000000000000000000000000X1001X",
            "mem[42] = 100",
            "mask = 00000000000000000000000000000000X0XX",
            "mem[26] = 1",
        )

        assertEquals(208L, communicator.solve(input).second)
    }
}