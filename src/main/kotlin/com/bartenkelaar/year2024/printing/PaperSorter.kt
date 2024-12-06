package com.bartenkelaar.year2024.printing

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.containsAny
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.partitionPer
import com.bartenkelaar.util.toIntList

class PaperSorter : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val (ruleLines, stackLines) = input.partitionPer("")
        val rules = ruleLines.map { it.toIntList("|") }.map { (a, b) -> a to b }
        val stacks = stackLines.nonBlank().map { it.toIntList(",") }

        val precedes = rules.groupBy({ (a, _) -> a }) { (_, b) -> b }
        val (correct, incorrect) = stacks.partition { it.isSorted(precedes) }

        val follows = rules.groupBy({ (_, b) -> b }) { (a, _) -> a }
        val sorted = incorrect.map { it.sort(follows) }

        return correct.middleSum() to sorted.middleSum()
    }

    private fun List<Int>.isSorted(precedes: Map<Int, List<Int>>): Boolean {
        for (i in indices.reversed()) {
            val followers = precedes[get(i)] ?: emptySet()
            if (subList(0, i).containsAny(followers)) {
                return false
            }
        }
        return true
    }

    private fun List<Int>.sort(follows: Map<Int, List<Int>>): List<Int> {
        if (isEmpty()) return this
        val first = findFirst(first(), follows)
        return listOf(first) + (this - first).sort(follows)
    }

    private tailrec fun List<Int>.findFirst(test: Int, follows: Map<Int, List<Int>>): Int {
        val preceding = follows[test]?.intersect(this) ?: emptySet()
        return if (preceding.isNotEmpty()) findFirst(preceding.first(), follows) else test
    }

    private fun List<List<Int>>.middleSum() = sumOf { it[it.lastIndex / 2] }
}
