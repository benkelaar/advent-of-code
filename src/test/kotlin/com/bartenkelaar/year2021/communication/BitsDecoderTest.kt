package com.bartenkelaar.year2021.communication

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BitsDecoderTest {
    private val decoder = BitsDecoder()

    @Test
    fun `example1`() {
        val input = listOf("8A004A801A8002F478")

        val result = decoder.solve(input)

        assertEquals(16, result.first)
    }

    @Test
    fun `example2`() {
        val input = listOf("620080001611562C8802118E34")

        val result = decoder.solve(input)

        assertEquals(12, result.first)
    }
}