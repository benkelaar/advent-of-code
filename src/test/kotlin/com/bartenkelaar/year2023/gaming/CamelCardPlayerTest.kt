package com.bartenkelaar.year2023.gaming

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CamelCardPlayerTest {
    private val player = CamelCardPlayer()

    @Test
    fun `example should work`() {
        val input = listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483"
        )

        val (a, b) = player.solve(input)

        assertEquals(6440, a)
        assertEquals(5905, b)
    }
}