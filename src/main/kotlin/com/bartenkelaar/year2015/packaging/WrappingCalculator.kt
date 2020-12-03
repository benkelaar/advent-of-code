package com.bartenkelaar.year2015.packaging

import com.bartenkelaar.Solver

class WrappingCalculator : Solver {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val dimensions = input.map { Dimension.forString(it) }
        return dimensions.map { it.paperRequired() }.sum() to
                dimensions.map { it.ribbonRequired() }.sum()
    }
}

data class Dimension(
    val low: Int,
    val mid: Int,
    val high: Int,
) {
    fun paperRequired() = 3 * low * mid + 2 * (low * high + mid * high)
    fun ribbonRequired() = 2 * (low + mid) + low * mid * high

    companion object {
        fun forString(str: String): Dimension {
            val (low, mid, high) = str.trim().split("x").map { it.toInt() }.sorted()
            return Dimension(low, mid, high)
        }
    }
}