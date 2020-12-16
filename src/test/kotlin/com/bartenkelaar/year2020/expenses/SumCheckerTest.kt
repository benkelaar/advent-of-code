package com.bartenkelaar.year2020.expenses

import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.Test

class SumCheckerTest {
    private val sumChecker = SumChecker()

    @Test
    fun `given list, find pair that sums to 2020`() {
        assertEquals(
            expected = 1000L to 1020L,
            actual = sumChecker.findPairSum(setOf(1000, 1010, 1020))
        )
    }

    @Test
    fun `given list, find pair that sums to 2020, no doubles`() {
        assertEquals(
            expected = 1000L to 1020L,
            actual = sumChecker.findPairSum(setOf(1010, 1000, 1020))
        )
    }

    @Test
    fun `given list, find triple that sums to 2020`() {
        assertEquals(
            expected = listOf(1010L, 500, 510),
            actual = sumChecker.findTripleSum(setOf(1000, 1010, 1020, 500, 510))
        )
    }

    @Test
    fun `given list without pair summing to 2020, return null`() {
        assertNull(sumChecker.findPairSum(setOf(100, 110, 120)))
    }
}