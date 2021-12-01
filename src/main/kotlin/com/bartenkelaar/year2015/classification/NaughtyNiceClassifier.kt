package com.bartenkelaar.year2015.classification

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

private val NAUGHTY_PAIRS = listOf("ab", "cd", "pq", "xy")

class NaughtyNiceClassifier : Solver() {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val vowely = input.nonBlank().filter { it.hasThreeVowels() }
        val doubly = vowely.filter { it.containsDoubleLetter() }
        return doubly.count { it.hasNoNaughtyCouples() } to 0
    }

    private fun String.hasThreeVowels() = count { "aeiou".contains(it) } > 2
    private fun String.containsDoubleLetter() = zipWithNext().any { (first, second) -> first == second }
    private fun String.hasNoNaughtyCouples() = NAUGHTY_PAIRS.none { it in this }
}

