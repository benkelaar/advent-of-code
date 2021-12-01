package com.bartenkelaar.year2020.gaming

import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

@Disabled
class CombatTest {
    private val combat = Combat()

    @Test
    fun `run example`() {
        val input = listOf(
            "Player 1:",
            "9",
            "2",
            "6",
            "3",
            "1",
            "",
            "Player 2:",
            "5",
            "8",
            "4",
            "7",
            "10",
        )

        val result = combat.solve(input)

        assertEquals(291, result.second)
    }

    @Test
    fun `run recursion example`() {
        val input = listOf(
            "Player 1:",
            "43",
            "19",
            "",
            "Player 2:",
            "2",
            "29",
            "14",
        )

        val result = combat.solve(input)

        assertEquals(105, result.second)
    }
}