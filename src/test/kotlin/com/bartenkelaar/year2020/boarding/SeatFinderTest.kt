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

    @Test
    fun `given boarding pass code, calculate right row`() {
        assertEquals(70, Seat.parseFrom("BFFFBBFRRR").row)
        assertEquals(14, Seat.parseFrom("FFFBBBFRRR").row)
        assertEquals(102, Seat.parseFrom("BBFFBBFRLL").row)
    }

    @Test
    fun `given boarding pass code, calculate right column`() {
        assertEquals(7, Seat.parseFrom("BFFFBBFRRR").column)
        assertEquals(7, Seat.parseFrom("FFFBBBFRRR").column)
        assertEquals(4, Seat.parseFrom("BBFFBBFRLL").column)
    }
}