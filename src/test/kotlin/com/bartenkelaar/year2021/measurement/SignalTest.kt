package com.bartenkelaar.year2021.measurement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SignalTest {
    @Test
    fun `test example`() {
        val signal = Signal.forLine("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

        assertEquals(5353, signal.value)
    }
}