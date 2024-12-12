package com.bartenkelaar.year2024.production

import kotlin.test.Test
import kotlin.test.assertEquals

class PlantFencingTest {
    @Test
    fun `simple test`() {
        val input = listOf(
            "RRRRIICCFF",
            "RRRRIICCCF",
            "VVRRRCCFFF",
            "VVRCCCJFFF",
            "VVVVCJJCFE",
            "VVIVCCJJEE",
            "VVIIICJJEE",
            "MIIIIIJJEE",
            "MIIISIJEEE",
            "MMMISSJEEE",
        )

        val result = PlantFencing().solve(input)

        assertEquals(1930 to 1206, result)
    }
}
