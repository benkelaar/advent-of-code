package com.bartenkelaar.year2024.operation

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.distanceTo
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.second
import com.bartenkelaar.util.splitWhiteSpace

class NuclearReportAssessor : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val rows = input.nonBlank().map { it.splitWhiteSpace().map { it.toInt() } }
        val safeCount = rows.count { it.isSafe() }
        val rowPermutations = rows.map { row ->
            row.indices.map { row.subList(0, it) + row.subList(it + 1, row.size) } }
        return safeCount to rowPermutations.count { it.any { it.isSafe() } }
    }

    private fun List<Int>.isSafe(): Boolean {
        val first = first()
        val second = second()
        if (first == second) return false

        val ascends = second > first
        val pairs = zipWithNext()

        return pairs.all { (a, b) ->
            (if (ascends) b > a else a > b) && a.distanceTo(b) < 4
        }
    }
}