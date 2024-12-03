package com.bartenkelaar.year2024.history

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.distanceTo
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.second
import com.bartenkelaar.util.splitWhiteSpace

class LocationFinder : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val distances = input.nonBlank().map { it.splitWhiteSpace().map { it.toInt() } }
        val lefts = distances.map { it.first() }.sorted()
        val rights = distances.map { it.second() }.sorted()
        val totalDistance = lefts.zip(rights)
            .sumOf { (first, second) -> first.distanceTo(second) }

        val uniqueLefts = lefts.toSet()
        val similarityScore =
            uniqueLefts.sumOf { it * lefts.occurrences(it) * rights.occurrences(it) }
        return totalDistance to similarityScore
    }

    private fun List<Int>.occurrences(i: Int) = count { it == i }
}

