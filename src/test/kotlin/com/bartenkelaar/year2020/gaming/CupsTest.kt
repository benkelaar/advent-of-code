package com.bartenkelaar.year2020.gaming

import com.bartenkelaar.util.shiftSo
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

class ListUtilTest {
    @Test
    fun `given test, expect things`() {
        assertEquals(listOf(3, 1, 2), listOf(1, 2, 3).shiftSo(3, 0))
    }
}