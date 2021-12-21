package com.bartenkelaar.year2020.gaming

import kotlin.test.Test
import kotlin.test.assertEquals

class CupsTest {
    private val cups = Cups()

    @Test
    fun `given example, expect example outcome`() {
        val input = listOf(3, 8, 9, 1, 2, 5, 4, 6, 7).map { it.toString() }

        assertEquals(67384529L, cups.solve(input).first)
    }
}