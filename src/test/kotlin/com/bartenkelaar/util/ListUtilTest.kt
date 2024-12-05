package com.bartenkelaar.util

import kotlin.test.Test
import kotlin.test.assertEquals

class ListUtilTest {
    @Test
    fun pivotTest() {
        val input = listOf(listOf(0, 1, 2), listOf(3, 4, 5))

        val actual = input.pivot()

        assertEquals(listOf(listOf(0, 3), listOf(1, 4), listOf(2, 5)), actual)
    }
}
