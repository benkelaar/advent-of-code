package com.bartenkelaar.year2024.gaming

import kotlin.test.Test
import kotlin.test.assertEquals

class StoneBlinkerTest {
    @Test
    fun `simple example`() {
        val (result, _) = StoneBlinker(6).solve(listOf("125 17"))

        assertEquals(22, result)
    }
}
