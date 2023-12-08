package com.bartenkelaar.year2023.navigation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

class DesertMapReaderTest {
    private val reader = DesertMapReader()

    @Test
    fun `The example should work`() {
        val input = listOf(
            "RL",
            "",
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)",
        )

        val (a, _) = reader.solve(input)

        assertEquals(a, 2)
    }

    @Test
    fun `The rotating example should work`() {
        val input = listOf(
            "LLR",
            "",
            "AAA = (BBB, BBB)",
            "BBB = (AAA, ZZZ)",
            "ZZZ = (ZZZ, ZZZ)",
        )

        val (a, _) = reader.solve(input)

        assertEquals(a, 6)
    }

    @Test
    @Ignore
    fun `The second example should work`() {
        val input = listOf(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)",
        )

        val (_, b) = reader.solve(input)

        assertEquals( 6L, b)
    }
}