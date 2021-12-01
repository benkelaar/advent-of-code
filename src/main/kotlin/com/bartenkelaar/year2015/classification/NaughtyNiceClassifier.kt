package com.bartenkelaar.year2015.classification

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

private val NAUGHTY_PAIRS = listOf("ab", "cd", "pq", "xy")

class NaughtyNiceClassifier : Solver() {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val words = input.nonBlank()
        val originalNiceWordCount = words.filter { it.hasThreeVowels() }
            .filter { it.containsDoubleLetter() }
            .count { it.hasNoNaughtyCouples() }
        val updatedNiceWordCount = words
            .filter { it.hasDoublePair() }
            .count { it.hasSkippedDouble() }
        return originalNiceWordCount to updatedNiceWordCount
    }

    private fun String.hasThreeVowels() = count { "aeiou".contains(it) } > 2
    private fun String.containsDoubleLetter() = zipWithNext().any { (first, second) -> first == second }
    private fun String.hasNoNaughtyCouples() = NAUGHTY_PAIRS.none { it in this }

    private fun String.hasDoublePair() = zipWithNext()
            .mapIndexed { i, (a, b) -> this.substring(i + 2).contains("$a$b") }
            .any { it }
    private fun String.hasSkippedDouble() = windowed(3).any { it[0] == it[2] }
}

