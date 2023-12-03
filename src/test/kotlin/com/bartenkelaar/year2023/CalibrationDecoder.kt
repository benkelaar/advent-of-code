package com.bartenkelaar.year2023

import com.bartenkelaar.year2023.machinery.CalibrationDecoder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CalibrationDecoderTest {
    private val decoder = CalibrationDecoder()

    @Test
    fun `The example should work`() {
        val input = listOf("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet")

        val (a, _) = decoder.solve(input)

        assertEquals(a, 142)
    }

    @Test
    fun `second example`() {
        val input = listOf("two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen")

        val (a, _) = decoder.solve(input)

        assertEquals(a, 281)
    }
}