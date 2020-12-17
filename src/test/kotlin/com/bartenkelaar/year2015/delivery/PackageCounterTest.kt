package com.bartenkelaar.year2015.delivery

import kotlin.test.Test
import kotlin.test.assertEquals

class PackageCounterTest {
    private val counter = PackageCounter()

    @Test
    fun `given single chevron, expect two houses delivered`() {
        val input = listOf("^")
        assertEquals(2, counter.solve(input).first)
    }

    @Test
    fun `given multiple chevrons, expect one more house delivered`() {
        val input = listOf("^>v")
        assertEquals(4, counter.solve(input).first)
    }

    @Test
    fun `given opposite chevrons, doubles not counted`() {
        val input = listOf("^v<>")
        assertEquals(3, counter.solve(input).first)
    }
}