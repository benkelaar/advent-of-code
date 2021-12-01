package com.bartenkelaar.year2020.math

import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("DANGEROUS_CHARACTERS")
class ExpressionTest {
    @Test
    fun `given 1 + 2, calculate sum`() {
        val expression = Expression("1 + 2")

        val result = expression.solve()

        assertEquals(3, result.first)
    }

    @Test
    fun `given 2 * 3, calculate product`() {
        val expression = Expression("2 * 3")

        val result = expression.solve()

        assertEquals(6, result.first)
    }

    @Test
    fun `given 2 + 3 * 4, calculate product with left-precedence`() {
        val expression = Expression("2 + 3 * 4")

        val result = expression.solve()

        assertEquals(20, result.first)
    }

    @Test
    fun `given 2 + (3 * 4), calculate product with parentheses-precedence`() {
        val expression = Expression("2 + (3 * 4)")

        val result = expression.solve()

        assertEquals(14, result.first)
    }

    @Test
    fun `given 2 + (3 * (1 + 3)), calculate product with parentheses-precedence`() {
        val expression = Expression("2 + (3 * (1 + 3))")

        val result = expression.solve()

        assertEquals(14, result.first)
    }

    @Test
    fun `given multiple consecutive parentheses, do the right thing`() {
        val expression = Expression("2 + (3 * 4) * (1 + 1)")

        val result = expression.solve()

        assertEquals(28, result.first)
    }

    @Test
    fun `given complex, expect right answer`() {
        val expression = Expression("(7 * (3 + 8 + 8 + 7) + (6 + 8 * 2 + 5 + 2 * 6) * (5 + 2) * 9) + ((7 * 4 + 8) * 6 * 8 + 9) * 7 * 2 * 2")

        val result = expression.solve()

        assertEquals(740124, result.first)
    }
}

@Suppress("DANGEROUS_CHARACTERS")
class AdvancedExpressionTest {
    @Test
    fun `given 1 + 2, calculate sum`() {
        val expression = AdvancedExpression("1 + 2")

        val result = expression.solve()

        assertEquals(3, result.first)
    }

    @Test
    fun `given 2 * 3, calculate product`() {
        val expression = AdvancedExpression("2 * 3")

        val result = expression.solve()

        assertEquals(6, result.first)
    }

    @Test
    fun `given 2 + 3 * 4, calculate product with left-precedence`() {
        val expression = AdvancedExpression("2 + 3 * 4")

        val result = expression.solve()

        assertEquals(20, result.first)
    }

    @Test
    fun `given 2 + (3 * 4), calculate product with parentheses-precedence`() {
        val expression = AdvancedExpression("1 + (3 * 4) + 1")

        val result = expression.solve()

        assertEquals(14, result.first)
    }

    @Test
    fun `given 2 * 3 + 4, calculate product with plus-precedence`() {
        val expression = AdvancedExpression("2 * 3 + 4")

        val result = expression.solve()

        assertEquals(14, result.first)
    }

    @Test
    fun `given 2 + (3 * 1 + 3), calculate product with parentheses-precedence`() {
        val expression = AdvancedExpression("2 + (3 * 1 + 3)")

        val result = expression.solve()

        assertEquals(14, result.first)
    }

    @Test
    fun `given multiple consecutive parentheses, do the right thing`() {
        val expression = AdvancedExpression("2 + (3 * 4) * (1 * 2)")

        val result = expression.solve()

        assertEquals(28, result.first)
    }

    @Test
    fun `given complex, expect right answer`() {
        val expression = AdvancedExpression("(7 * (3 + 8 + 8 + 7) + (6 + 8 * 2 + 5 + 2 * 6) * (5 + 2) * 9) + ((7 * 4 + 8) * 6 * 8 + 9) * 7 * 2 * 2")

        val result = expression.solve()

        assertEquals(9896040, result.first)
    }

    @Test
    fun testExamples() {
        assertEquals(51L, AdvancedExpression("1 + (2 * 3) + (4 * (5 + 6))").solve().first)
        assertEquals(46L, AdvancedExpression("2 * 3 + (4 * 5)").solve().first)
        assertEquals(1445L, AdvancedExpression("5 + (8 * 3 + 9 + 3 * 4 * 3)").solve().first)
        assertEquals(669060L, AdvancedExpression("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))").solve().first)
        assertEquals(23340L, AdvancedExpression("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2").solve().first)
    }
}