package com.bartenkelaar.year2023.gaming

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BoatRacerTest {
    private val racer = BoatRacer()

    @Test
    fun `example should work`() {
        val input = listOf(
            "Time:      7  15   30",
            "Distance:  9  40  200"
        )

        assertEquals(288, racer.solve(input).first)
        assertEquals(71503, racer.solve(input).second)
    }
}