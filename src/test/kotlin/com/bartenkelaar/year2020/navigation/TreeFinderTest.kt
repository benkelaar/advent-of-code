package com.bartenkelaar.year2020.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class TreeFinderTest {
    private val treeFinder = TreeFinder()

    @Test
    fun `given pattern, count trees encountered by 1-3 slope`() {
        val lines = listOf(
            ".....",
            "...#.",
            ".#..."
        )

        assertEquals(2, treeFinder.solve(lines).first)
    }

    @Test
    fun `given pattern, count trees encountered by slopes multiplied`() {
        val lines = listOf(
            ".....",
            "####.",
            "##..."
        )

        assertEquals(4L, treeFinder.solve(lines).second)
    }
}