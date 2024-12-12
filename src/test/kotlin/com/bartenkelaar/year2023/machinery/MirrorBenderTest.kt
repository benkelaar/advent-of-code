package com.bartenkelaar.year2023.machinery

import kotlin.test.Test
import kotlin.test.assertEquals

class MirrorBenderTest {
    @Test
    fun `simple example`() {
        val input = listOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#....",
        )

        val result = MirrorBender().solve(input)

        assertEquals(136 to 64, result)
    }
}
