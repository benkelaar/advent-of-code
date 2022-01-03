package com.bartenkelaar.year2021.movement

import com.bartenkelaar.year2021.SeaCucumberMoverYear21Day25
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SeaCucumberMoverTest {
    private val mover = SeaCucumberMoverYear21Day25()

    @Test
    fun `example`() {
        val input = listOf(
            "v...>>.vv>",
            ".vv>>.vv..",
            ">>.>v>...v",
            ">>v>>.>.v.",
            "v>v.vv.v..",
            ">.>>..v...",
            ".vv..>.>v.",
            "v.v..>>v.v",
            "....v..v.>"
        )

        val result = mover.solve(input)

        assertEquals(58, result.first)
    }
}