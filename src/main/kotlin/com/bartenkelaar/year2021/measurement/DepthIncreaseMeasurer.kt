package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class DepthIncreaseMeasurer : Solver() {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val measurements = input.nonBlank().map { it.toInt() }
        val singleIncreased = measurements.countIncreases()
        val triplesIncreased = measurements.windowed(3).map { it.sum() }.countIncreases()
        return singleIncreased to triplesIncreased
    }

    private fun List<Int>.countIncreases() = zipWithNext().count { (a, b) -> b > a }
}