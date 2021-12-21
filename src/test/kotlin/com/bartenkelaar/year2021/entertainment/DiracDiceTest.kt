package com.bartenkelaar.year2021.entertainment

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DiracDiceTest {
    private val dice = DiracDice()

    @Test
    fun `example`() {
        val input = listOf("Player 1 starting position: 4", "Player 2 starting position: 8")

        val result = dice.solve(input)

        assertEquals(739785, result.first)
        assertEquals(444356092776315L, result.second)
    }
}