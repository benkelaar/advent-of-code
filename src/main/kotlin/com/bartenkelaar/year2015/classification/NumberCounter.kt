package com.bartenkelaar.year2015.classification

import com.bartenkelaar.util.Solver

class NumberCounter : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val regex = """(\d)\1*""".toRegex()
        val numbers = input.first()

        var fortyLength = 0
        var nextNumbers = numbers
        (1..50).map { turn ->
            nextNumbers = regex.findAll(nextNumbers).map { it.value.length.toString() + it.groups[1]!!.value }.joinToString("")
            if (turn == 40) fortyLength = nextNumbers.length
        }
        return fortyLength to nextNumbers.length
    }
}
