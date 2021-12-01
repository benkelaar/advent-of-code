package com.bartenkelaar.year2020.adapters

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class JoltChecker : Solver() {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val sorted = input.nonBlank().map { it.toLong() }.sorted()
        val ordered = listOf(0, *sorted.toTypedArray(), sorted.last() + 3)
        val differences = ordered.slice(0 until ordered.lastIndex).mapIndexed { i, jolt -> ordered[i+1] - jolt}
        val jumpProduct = differences.count { it == 1L } * differences.count { it == 3L}
        return jumpProduct to ordered.countPaths()
    }

    private fun List<Long>.countPaths(): Long {
        val nodeCounts = mutableMapOf(last() to 1L)
        reversed().slice(1..lastIndex).forEach { node ->
            nodeCounts[node] = findDescendants(node).map { nodeCounts.getValue(it) }.sum()
        }
        return nodeCounts[0]!!
    }

    private fun List<Long>.findDescendants(from: Long) = filter { it > from && it - from <= 3 }
}

