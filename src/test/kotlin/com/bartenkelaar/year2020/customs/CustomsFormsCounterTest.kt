package com.bartenkelaar.year2020.customs

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class CustomsFormsCounterTest {
    private val counter = CustomsFormsCounter()

    @Test
    fun `given custom forms groups, count different answers and sum`() {
        val input = listOf("ab", "b", "", "c", "a", "b", "", "abcd")

        assertEquals(9, counter.solve(input).first)
    }
}