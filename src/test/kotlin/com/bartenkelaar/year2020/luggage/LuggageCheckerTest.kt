package com.bartenkelaar.year2020.luggage

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LuggageCheckerTest {
    private val luggageChecker = LuggageChecker()

    @Test
    fun `given simple input, count 2`() {
        val input = listOf(
            "bright white bags contain 1 shiny gold bag.",
            "faded brown bags contain 1 shiny gold bag.",
        )

        assertEquals(2, luggageChecker.solve(input).first)
    }

    @Test
    fun `given layered input, aggregate correctly`() {
        val input = listOf(
            "bright white bags contain 1 shiny gold bag.",
            "faded brown bags contain 1 shiny gold bag.",
            "muted green bags contain 1 dark black bag, 2 faded brown bags.",
            "dirty grey bags contain 1 dark black bag",
        )

        assertEquals(3, luggageChecker.solve(input).first)
    }

    @Test
    fun `given example input, count bags in shiny gold bag correctly`() {
        val input = listOf(
            "shiny gold bags contain 2 dark red bags.",
            "dark red bags contain 2 dark orange bags.",
            "dark orange bags contain 2 dark yellow bags.",
            "dark yellow bags contain 2 dark green bags.",
            "dark green bags contain 2 dark blue bags.",
            "dark blue bags contain 2 dark violet bags.",
            "dark violet bags contain no other bags.",
        )

        assertEquals(126, luggageChecker.solve(input).second)
    }
}

class LuggageRuleTest {
    @Test
    fun `given rule line, parse it correctly`() {
        val line = "bright white bags contain 1 shiny gold bag."

        assertEquals(
            LuggageRule(
                color = Color("bright", "white"),
                conditions = listOf(Condition(Color("shiny", "gold"), 1))
            ), LuggageRule.forLine(line)
        )
    }

    @Test
    fun `given empty bag rule, skip it`() {
        val line = "dotted black bags contain no other bags."

        assertNull(LuggageRule.forLine(line))
    }
}

class ColorTest {
    @Test
    fun `given color ID, parse it correctly`() {
        assertEquals(Color("bright", "white"), Color("bright white bags"))
    }
}