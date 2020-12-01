package com.bartenkelaar.expenses

import kotlin.test.assertEquals
import kotlin.test.Test

class AddUpResolverTest {
    private val resolver = AddUpResolver(SumChecker())

    @Test
    fun `given list, find added up product`() {
        assertEquals(resolver.resolve(setOf(1000, 1010, 1020, 490, 510)), 1020000 to 254898000)
    }
}