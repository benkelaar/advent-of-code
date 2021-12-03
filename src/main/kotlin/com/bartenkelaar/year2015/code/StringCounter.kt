package com.bartenkelaar.year2015.code

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class StringCounter : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val lines = input.nonBlank()
        val totalCharacters = lines.sumOf { it.length }

        val totalInMemoryCharacters = lines.asSequence()
            .map { it.substring(1 until it.lastIndex) }
            .map { it.replace("\\\\", "x") }
            .map { it.replace("\\\"", "x") }
            .map { it.replace("""\\x[0-9a-f]{2}""".toRegex(), "x") }
            .sumOf { it.length }

        val totalEscapedCharacters = lines.sumOf { it.length + it.count { c -> c in listOf('"', '\\') } + 2 }
        return totalCharacters - totalInMemoryCharacters to totalEscapedCharacters - totalCharacters
    }
}