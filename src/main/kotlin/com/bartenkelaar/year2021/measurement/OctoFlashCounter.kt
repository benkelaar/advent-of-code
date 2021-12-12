package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class OctoFlashCounter : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val grid = input.nonBlank()
            .map { row -> row.split("").nonBlank().map { it.toInt() }.toMutableList() }

        var flashes = 0
        var syncTurn = 0
        var flashesAt100 = 0
        for (turn in 1..500) {
            val stepFlashes = grid.runStep()
            flashes += stepFlashes
            syncTurn = turn
            if (turn == 100) flashesAt100 = flashes
            if (stepFlashes == 100) {
                syncTurn = turn
                break
            }
        }
        return flashesAt100 to syncTurn
    }

    private fun List<MutableList<Int>>.runStep(): Int {
        forEach { row -> row.forEachIndexed { i, o -> row[i] = o + 1 } }
        var flashes = 0
        while (any { row -> row.any { it > 9 }}) {
            for (x in 0..9) {
                for (y in 0..9) {
                    if (this[y][x] > 9) {
                        this[y][x] = 0
                        flashes++
                        incrementNeighbours(x, y)
                    }
                }
            }
        }
        return flashes
    }

    private fun List<MutableList<Int>>.incrementNeighbours(x: Int, y: Int) {
        ((-1..1).flatMap { x1 -> (-1..1).map { y1 -> x1 to y1 } } - (0 to 0)).forEach { (dx, dy) ->
            val currentValue = this.getOrNull(y + dy)?.getOrNull(x + dx)
            if (currentValue != 0 && currentValue != null) this[y + dy][x + dx]++
        }
    }
}
