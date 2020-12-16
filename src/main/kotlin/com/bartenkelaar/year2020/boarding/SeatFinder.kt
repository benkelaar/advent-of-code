package com.bartenkelaar.year2020.boarding

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank

class SeatFinder : Solver {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val seats = input.nonBlank()
            .map { it.toSeatId() }
            .sorted()
        return seats.last() to seats.findFirstOpen()
    }
}

private fun String.toSeatId() =
    map { it.toBinary() }
        .joinToString("")
        .toInt(2)

private fun List<Int>.findFirstOpen() =
    filterIndexed { i, id -> id != last() && (next(i) - id != 1) }
        .first() + 1

private fun Char.toBinary() = if (this == 'B' || this == 'R') '1' else '0'

private fun <T> List<T>.next(index: Int) = get(index + 1)
