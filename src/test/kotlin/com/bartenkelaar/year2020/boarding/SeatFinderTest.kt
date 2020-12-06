package com.bartenkelaar.year2020.boarding

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class SeatFinderTest {
    private val seatFinder = SeatFinder()

    @Test
    fun `given boarding passes, find max ID`() {
        val lines = listOf("BFFFBBFRRR", "FFFBBBFRRR", "BBFFBBFRLL")

        assertEquals(820, seatFinder.solve(lines).first)
    }
}