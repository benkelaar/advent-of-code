package com.bartenkelaar.year2020.gaming

import kotlin.test.Test
import kotlin.test.assertEquals

class GameRunnerTest {
    private val runner = GameRunner()

    @Test
    fun `testScenario`() {
        val instructions = """
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
        """.trimMargin().split("\n")

        assertEquals(5 to 8, runner.solve(instructions))
    }
}