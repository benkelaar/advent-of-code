package com.bartenkelaar.year2020.boarding

import kotlin.test.Test
import kotlin.test.assertEquals

class BusFinderTest {
    @Test
    fun findConsecutive() {
        assertEquals(49, findConsecutiveDepartureTime(listOf(5, 7, 9, 17), mapOf(7L to 0L, 5L to 1L, 17L to 2L, 9L to 5L)))
    }

    @Test
    fun findConsecutiveExamples() {
        assertEquals(3417, findConsecutiveDepartureTime(listOf(17,13,19), mapOf(17L to 0L, 13L to 2L, 19L to 3)))
        assertEquals(754018, findConsecutiveDepartureTime(listOf(67,7,59,61), listOf(67L,7,59,61).indexMap()))
        assertEquals(1202161486, findConsecutiveDepartureTime(listOf(1789,37,47,1889), listOf(1789L,37,47,1889).indexMap()))
        assertEquals(779210, findConsecutiveDepartureTime(listOf(67,7,59,61), mapOf(67L to 0L, 7L to 2L, 59L to 3, 61L to 4L)))
    }
}

private fun <E> List<E>.indexMap() = mapIndexed { i, e -> e to i.toLong() }.toMap()
