package com.bartenkelaar.year2020.expenses

import kotlin.test.assertEquals
import kotlin.test.Test

class AddUpResolverTest {
    private val resolver = AddUpResolver(SumChecker())

    @Test
    fun `given list, find added up product`() {
        assertEquals(resolver.resolve(setOf(1000, 1010, 1020, 490, 510)), 1020000L to 254898000L)
    }
}