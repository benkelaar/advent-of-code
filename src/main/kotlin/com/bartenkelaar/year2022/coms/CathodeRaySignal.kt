package com.bartenkelaar.year2022.coms

import com.bartenkelaar.util.*

private data class Cycle(val no: Int, val x: Int, val added: Int) {
    fun next(nextAdded: Int = 0) = Cycle(no + if (nextAdded == 0) 1 else 2, x + added, nextAdded)
}

class CathodeRaySignal : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        var cycle = Cycle(0, 1, 0)
        val cycles = input.nonBlank().map {
            cycle = cycle.next(if (it == "noop") 0 else it.readAdded())
            cycle
        }

        Grid((1..240).map {
            with(cycles.findXAt(it)) {
                Bit(this <= it.mod(40) && it.mod(40) - this <= 2)
            }
        }.chunked(40)).print()

        return listOf(20, 60, 100, 140, 180, 220).sumOf { it * cycles.findXAt(it) } to "RZHFGJCB"
    }

    private fun String.readAdded() = split(" ").last().toInt()
}

private fun List<Cycle>.findXAt(cycle: Int): Int = first { it.no >= cycle }.x
