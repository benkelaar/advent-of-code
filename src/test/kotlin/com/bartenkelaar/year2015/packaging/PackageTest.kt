package com.bartenkelaar.year2015.packaging

import kotlin.test.Test
import kotlin.test.assertEquals

class PackageTest {
    @Test
    fun `given dimension string, extract proper dimensions`() {
        assertEquals(Package.forString("1x3x2"), Package(1, 2, 3))
    }

    @Test
    fun `given dimensions, calculate paper required`() {
        assertEquals(24, Package(1, 2, 3).paperRequired())
    }
}
