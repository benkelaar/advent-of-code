package com.bartenkelaar.year2021.movement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NavigationFixerTest {
    private val fixer = NavigationFixer()

    @Test
    fun `example case`() {
        val input = listOf(
            "[({(<(())[]>[[{[]{<()<>>",
            "[(()[<>])]({[<{<<[]>>(",
            "{([(<{}[<>[]}>{[]{[(<()>",
            "(((({<>}<{<{<>}{[]{[]{}",
            "[[<[([]))<([[{}[[()]]]",
            "[{[{({}]{}}([{[{{{}}([]",
            "{<[[]]>}<{[{[{[]{()[[[]",
            "[<(<(<(<{}))><([]([]()",
            "<{([([[(<>()){}]>(<<{{",
            "<{([{{}}[<[[[<>{}]]]>[]]"
        )

        val actual = fixer.solve(input)

        assertEquals(26397, actual.first)
    }
}
