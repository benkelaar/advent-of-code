package com.bartenkelaar.year2024.operation

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class MullComputer : Solver() {
    private val mulRegex = "mul\\((\\d+),(\\d+)\\)".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val multipliedSum = input.nonBlank().sumOf { it.sumOfMultiples() }

        val oneLine = input.reduce(String::plus)
        val doLines = oneLine.split("do\\(\\)".toRegex()).mapNotNull {
            it.split("don't\\(\\)".toRegex()).firstOrNull()
        }
        return multipliedSum to doLines.sumOf { it.sumOfMultiples() }
    }

    private fun String.sumOfMultiples(): Int =
        mulRegex.findAll(this).map { it.groupValues }
            .sumOf { (_, a, b) -> a.toInt() * b.toInt() }
}