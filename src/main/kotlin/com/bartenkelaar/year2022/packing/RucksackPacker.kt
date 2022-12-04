package com.bartenkelaar.year2022.packing

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class RucksackPacker : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val elfRucksacks = input.nonBlank()
        val packingFaultPriorities = elfRucksacks
            .map { it.splitInTwo() }
            .map { (first, second) -> first.first { it in second } }
            .sumOf { it.priority() }

        val badgePriorities = elfRucksacks.chunked(3)
            .map { (first, second, third) -> first.first { it in second && it in third } }
            .sumOf { it.priority() }

        return packingFaultPriorities to badgePriorities
    }

    private fun String.splitInTwo() = substring(0, length / 2) to substring(length / 2, length)
    private fun Char.priority() = if (code > 96) code - 96 else code - 38
}