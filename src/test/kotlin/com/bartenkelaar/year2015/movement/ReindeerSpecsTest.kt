package com.bartenkelaar.year2015.movement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ReindeerSpecsTest {
    @Test
    fun `test example case`() {
        val comet = ReindeerSpecs("Comet", 14, 10, 127)
        val dancer = ReindeerSpecs("Dancer", 16, 11, 162)

        assertEquals(1120, comet.travelledIn(1000))
        assertEquals(1056, dancer.travelledIn(1000))
    }
}