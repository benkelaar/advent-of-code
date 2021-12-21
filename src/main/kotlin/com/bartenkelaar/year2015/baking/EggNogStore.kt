package com.bartenkelaar.year2015.baking

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.tail

class EggNogStore : Solver() {
    override fun solve(input: List<String>) = findCombinations(input.parse(), 150).combinationCounts()

    private fun List<List<Int>>.combinationCounts() = size to count { cs -> cs.size == minOf { cms -> cms.size } }

    private tailrec fun findCombinations(containers: List<Int>, liquid: Int, found: List<List<Int>> = emptyList()): List<List<Int>> =
        if (containers.isEmpty()) found.filter { it.sum() == liquid }
        else {
            val current = containers.first()
            findCombinations(
                containers = containers.tail(),
                liquid = liquid,
                found = found + found.map { it + current } + listOf(listOf(current))
            )
        }

    private fun List<String>.parse() = nonBlank().map { it.toInt() }
}
