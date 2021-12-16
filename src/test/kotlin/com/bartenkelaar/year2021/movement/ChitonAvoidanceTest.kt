package com.bartenkelaar.year2021.movement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChitonAvoidanceTest {
    private val avoidance = ChitonAvoidance()

    @Test
    fun `example`() {
        val input = listOf(
            "1163751742",
            "1381373672",
            "2136511328",
            "3694931569",
            "7463417111",
            "1319128137",
            "1359912421",
            "3125421639",
            "1293138521",
            "2311944581"
        )

        assertEquals(40 to 315, avoidance.solve(input))
    }
}