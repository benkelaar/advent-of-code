package com.bartenkelaar.year2020.passwords

import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordCheckerTest {
    private val checker = PasswordChecker()

    @Test
    fun `given example list, count correct amount of occurrences`() {
        val lines = listOf("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc")

        assertEquals(2 to 1, checker.solve(lines))
    }
}