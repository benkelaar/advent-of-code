package com.bartenkelaar.year2023.farming

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.toIntList

class OasisEnvironmentPredictor : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val sequences = input.nonBlank().map { it.toIntList() }
        val extrapolations = sequences.map { extrapolate(it) }
        return extrapolations.sumOf { it.last() } to extrapolations.sumOf { it.first() }
    }

    private fun extrapolate(sequence: List<Int>): List<Int> {
        if (sequence.all { it == 0 }) return listOf(0) + sequence + 0
        val diffs = extrapolate(sequence.zipWithNext().map { (a, b) -> b - a })
        return listOf(sequence.first() - diffs.first()) + sequence + (sequence.last() + diffs.last())
    }
}