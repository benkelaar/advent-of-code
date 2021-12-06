package com.bartenkelaar.year2021.measurement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HydroMeasurerTest {
    private val measurer = HydroMeasurer()

    @Test
    fun `Test default example`() {
        val input = listOf(
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"
        )

        assertEquals(5 to 0, measurer.solve(input))
    }
}