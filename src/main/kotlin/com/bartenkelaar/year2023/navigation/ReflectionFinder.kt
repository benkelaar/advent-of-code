package com.bartenkelaar.year2023.navigation

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.pivot
import com.bartenkelaar.util.zipPerEmpty
import kotlin.math.min

class ReflectionFinder : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val patterns = input.zipPerEmpty()
        return patterns.sumReflectionScores { it.findReflectionIndex() } to
                patterns.sumReflectionScores { it.findSmudgedReflectionIndex() }
    }

    private fun List<List<String>>.sumReflectionScores(matchIndex: (List<String>) -> Int?) = sumOf { rows ->
        val columns = rows.map { it.toCharArray().toList() }.pivot().map { it.joinToString() }
        val reflectionIndex = matchIndex(columns)
        reflectionIndex?.let { it + 1 } ?: ((matchIndex(rows)!! + 1) * 100)
    }

    private fun List<String>.findSmudgedReflectionIndex() = indices.find { i ->
        if (i == lastIndex) return@find false
        val reflectionSize = min(i + 1, lastIndex - i)
        val columnNumber = i + 1
        val fromRows = subList(columnNumber - reflectionSize, columnNumber)
        val toRows = subList(columnNumber, columnNumber + reflectionSize).reversed()
        fromRows.zip(toRows).sumOf { (s1, s2) -> s1.zip(s2).count { (c1, c2) -> c1 != c2 } } == 1
    }

    private fun List<String>.findReflectionIndex() = indices.find { i ->
        if (i == lastIndex) return@find false
        val reflectionSize = min(i + 1, lastIndex - i)
        val columnNumber = i + 1
        subList(columnNumber - reflectionSize, columnNumber) ==
                subList(columnNumber, columnNumber + reflectionSize).reversed()
    }
}