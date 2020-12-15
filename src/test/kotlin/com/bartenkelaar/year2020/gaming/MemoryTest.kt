package com.bartenkelaar.year2020.gaming

import kotlin.test.Test
import kotlin.test.assertEquals

class MemoryTest {
    @Test
    fun example() {
        val memory = Memory(10)
        assertEquals(0, memory.solve(listOf("0","3","6")).first)
    }

    @Test
    fun moreExamples() {
        val memory = Memory(2020)
        assertEquals(1, memory.solve(listOf("1","3","2")).first)
        assertEquals(1836, memory.solve(listOf("3","1","2")).first)
    }
}