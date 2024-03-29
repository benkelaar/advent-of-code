package com.bartenkelaar.year2015.delivery

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Coordinate.Companion.ORIGIN
import com.bartenkelaar.util.Solver

class PackageCounter : Solver() {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val instructions = input.first().toCharArray().toList()
        return houseDeliveries(instructions).size to
                (houseDeliveries(instructions.filterIndexed { i, _ -> i % 2 == 0}) +
                houseDeliveries(instructions.filterIndexed { i, _ -> i % 2 != 0})).size
    }

    private fun houseDeliveries(instructions: List<Char>): Map<Coordinate, Int> {
        val houseGifts = mutableMapOf(ORIGIN to 1)
        var location = ORIGIN
        instructions.forEach {
            location = location.move(it)
            houseGifts.compute(location) { _, value -> value?.inc() ?: 1 }
        }
        return houseGifts.toMap()
    }
}

private fun Coordinate.move(c: Char) =
    when (c) {
        '^' -> Coordinate(x, y + 1)
        '>' -> Coordinate(x + 1, y)
        'v' -> Coordinate(x, y - 1)
        '<' -> Coordinate(x - 1, y)
        else -> throw IllegalArgumentException("Unknown direction: '$c'")
    }