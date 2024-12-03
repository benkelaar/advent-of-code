package com.bartenkelaar.year2021.simulation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LanternfishGrowthTest {
    private val growth = LanternFishGrowth()

    @Test
    fun `test example case`() {
        val actual = growth.solve(listOf("3,4,3,1,2"))

        assertEquals(5934L, actual.first)
    }
}