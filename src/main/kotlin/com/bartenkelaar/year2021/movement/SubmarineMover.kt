package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class SubmarineMover : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val inputSets: List<List<String>> = input.nonBlank().map { it.split(" ") }
        val totals = inputSets.groupBy { line -> line.first() }

        val forward = totals.sumAll("forward")
        val down = totals.sumAll("down")
        val up = totals.sumAll("up")

        val aimedDepth = calculateAimedDepth(inputSets)

        return (forward * (down - up)) to forward * aimedDepth
    }

    private fun calculateAimedDepth(inputSets: List<List<String>>): Int {
        var aimedDepth = 0
        var aim = 0
        inputSets.forEach { (dir, d) ->
            when (dir) {
                "forward" -> aimedDepth += aim * d.toInt()
                "down" -> aim += d.toInt()
                "up" -> aim -= d.toInt()
            }
        }
        return aimedDepth
    }

    private fun Map<String, List<List<String>>>.sumAll(key: String) = getValue(key).sumOf { line -> line.last().toInt() }
}