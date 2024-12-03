package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.Solver

class LensProductionInitializer : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        return input.first().split(",").sumOf { it.toHASH() } to 0
    }

    private fun String.toHASH(): Long {
        var value = 0L
        forEach {
            val code = it.code
            value += code
            value *= 17
            value %= 256
        }
        return value
    }
}