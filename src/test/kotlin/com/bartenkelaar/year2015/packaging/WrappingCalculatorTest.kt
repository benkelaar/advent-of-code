package com.bartenkelaar.year2015.packaging

import kotlin.test.Test
import kotlin.test.assertEquals

class WrappingCalculatorTest {
    private val calculator = WrappingCalculator()

    @Test
    fun `given two dimensions, calculate total correctly`() {
        assertEquals(24 to 0, calculator.solve(listOf("1x2x3")))
    }
}