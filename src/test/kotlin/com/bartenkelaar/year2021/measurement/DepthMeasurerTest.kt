package com.bartenkelaar.year2021.measurement

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DepthIncreaseMeasurerTest {
    val measurer = DepthIncreaseMeasurer()

    @Test
    fun `Handles the example case`() {
        val input = listOf(
            "199",
            "200",
            "208",
            "210",
            "200",
            "207",
            "240",
            "269",
            "260",
            "263"
        )

        assertEquals(measurer.solve(input), 7 to 5)
    }
}