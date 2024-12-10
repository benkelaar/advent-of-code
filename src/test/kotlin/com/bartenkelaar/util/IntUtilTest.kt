package com.bartenkelaar.util

import kotlin.test.Test
import kotlin.test.assertEquals

class IntUtilTest {
    @Test
    fun `test gcd`() {
        assertEquals(5, gcd(5, 10))
        assertEquals(12, gcd(24, 36))
    }
}
