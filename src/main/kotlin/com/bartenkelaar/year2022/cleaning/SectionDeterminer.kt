package com.bartenkelaar.year2022.cleaning

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class SectionDeterminer : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val ranges = input.nonBlank()
            .map { line -> line.split(",", "-" ).map { it.toInt() } }
            .map { it.chunked(2).map { (from, to) -> from..to } }

        return ranges.count { (first, second) -> first.covers(second) || second.covers(first) } to
                ranges.count { (first, second) -> first.overlaps(second) }
    }

    private fun IntRange.covers(other: IntRange) = first <= other.first && last >= other.last
    private fun IntRange.overlaps(other: IntRange) = covers(other) || other.contains(first) || other.contains(last)
}