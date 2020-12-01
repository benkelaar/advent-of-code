package com.bartenkelaar.expenses

import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.Test

class SumCheckerTest {
    private val sumChecker = SumChecker()

    @Test
    fun `given list, find pair that sums to 2020`() {
        assertEquals(sumChecker.findPairSum(listOf(1000, 1010, 1020)), 1000 to 1020)
    }

    @Test
    fun `given list without pair summing to 2020, return null`() {
        assertNull(sumChecker.findPairSum(listOf(100, 110, 120)))
    }
}