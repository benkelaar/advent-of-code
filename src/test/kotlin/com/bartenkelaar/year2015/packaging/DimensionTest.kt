package com.bartenkelaar.year2015.packaging

import kotlin.test.Test
import kotlin.test.assertEquals

class DimensionTest {
    @Test
    fun `given dimension string, extract proper dimensions`() {
        assertEquals(Dimension.forString("1x3x2"), Dimension(1, 2, 3))
    }

    @Test
    fun `given dimensions, calculate paper required`() {
        assertEquals(24, Dimension(1, 2, 3).paperRequired())
    }
}
