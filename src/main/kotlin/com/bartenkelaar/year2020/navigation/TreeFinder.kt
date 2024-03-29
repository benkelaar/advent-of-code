package com.bartenkelaar.year2020.navigation

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

private val SLOPES =
    listOf(
        1 to 1,
        1 to 3,
        1 to 5,
        1 to 7,
        2 to 1,
    )

class TreeFinder : Solver() {
    override fun solve(input: List<String>): Pair<Int, Number> {
        return input.countTrees(1 to 3) to
                SLOPES.map { input.countTrees(it) }.toLongProduct()
    }

    private fun List<String>.countTrees(angle: Pair<Int, Int>) = nonBlank()
            .filterIndexed { i, _ -> i % angle.first == 0 }
            .mapIndexed { i, line -> line[angle.second * i % line.length] }
            .count { it == '#' }

    private fun List<Int>.toLongProduct() = map { it.toLong() }.reduce(Long::times)
}