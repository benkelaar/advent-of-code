package com.bartenkelaar.year2023.machinery

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EngineSchematicReaderTest {
    private val reader = EngineSchematicReader()

    @Test
    fun `the example should work`() {
        val input = listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598..",
        )

         assertEquals(4361, reader.solve(input).first)
    }
}