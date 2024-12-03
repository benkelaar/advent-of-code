package com.bartenkelaar.year2024.operation

import com.bartenkelaar.util.Solver

class MullComputer : Solver() {
    private val mulRegex = "mul\\((\\d+),(\\d+)\\)".toRegex()
    private val doRegex = "do\\(\\)".toRegex()
    private val dontRegex = "don't\\(\\)".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val program = input.reduce(String::plus)

        val doSections = program.split(doRegex).map {
            it.split(dontRegex).first()
        }
        return program.sumOfMultiples() to doSections.sumOf { it.sumOfMultiples() }
    }

    private fun String.sumOfMultiples(): Int =
        mulRegex.findAll(this).map { it.groupValues }
            .sumOf { (_, a, b) -> a.toInt() * b.toInt() }
}