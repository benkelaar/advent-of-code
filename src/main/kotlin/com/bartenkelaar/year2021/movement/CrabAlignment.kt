package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.Solver
import kotlin.math.abs

class CrabAlignment : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val positions = input.first().split(',').map { it.toInt() }

        val naiveFuelSpent = findMinimumFuel(positions)
        val advancedFuelSpent = findMinimumFuel(positions) { ((it * (it + 1)) / 2) }
        return naiveFuelSpent to advancedFuelSpent
    }

    private fun findMinimumFuel(positions: List<Int>, transform: (Int) -> Int = { it }): Int {
        var previousFuelSpent = Int.MAX_VALUE
        var fuelSpent = Int.MAX_VALUE - 1
        var test = 0
        while (fuelSpent < previousFuelSpent) {
            previousFuelSpent = fuelSpent
            fuelSpent = positions.map { abs(it - test) }.sumOf(transform)
            test++
        }
        return previousFuelSpent
    }
}
