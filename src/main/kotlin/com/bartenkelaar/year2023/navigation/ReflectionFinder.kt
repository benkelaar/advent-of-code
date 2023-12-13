package com.bartenkelaar.year2023.navigation

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.pivot
import com.bartenkelaar.util.zipPerEmpty
import kotlin.math.min

typealias ReflectionMatcher = (List<String>, List<String>) -> Boolean

class ReflectionFinder : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val patterns = input.zipPerEmpty()
        return patterns.sumReflectionScores(::listsEqual) to
                patterns.sumReflectionScores(::oneCharDifferenceTotal)
    }

    private fun List<List<String>>.sumReflectionScores(matcher: ReflectionMatcher) = sumOf { rows ->
        val columns = rows.map { it.toCharArray().toList() }.pivot().map { it.joinToString() }
        val columnReflectionIndex = columns.findReflectionIndex(matcher)
        columnReflectionIndex ?: (rows.findReflectionIndex(matcher)!! * 100)
    }

    private fun List<String>.findReflectionIndex(matcher: ReflectionMatcher) = indices.find { i ->
        if (i == lastIndex) return@find false
        val reflectionSize = min(i + 1, lastIndex - i)
        val columnNumber = i + 1
        matcher(subList(columnNumber - reflectionSize, columnNumber),
            subList(columnNumber, columnNumber + reflectionSize).reversed())
    }?.let { it + 1 }

    private fun listsEqual(l1: List<String>, l2: List<String>) = l1 == l2

    private fun oneCharDifferenceTotal(l1: List<String>, l2: List<String>) =
        l1.zip(l2).sumOf { (s1, s2) -> s1.zip(s2).count { (c1, c2) -> c1 != c2 } } == 1
}