package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.Solver
import kotlin.math.abs

class CrabAlignment : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val positions = input.first().split(',').map { it.toInt() }

        var previousFuelSpent = Long.MAX_VALUE
        var fuelSpent = Long.MAX_VALUE - 1
        var test = 0
        while (fuelSpent < previousFuelSpent) {
            previousFuelSpent = fuelSpent
            fuelSpent = positions.map{ abs(it - test) }.sumOf { ((it*(it + 1))/2).toLong() }
            test++
        }
        return previousFuelSpent to 0
    }
}
