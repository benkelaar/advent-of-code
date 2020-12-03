package com.bartenkelaar.year2015.packaging

import com.bartenkelaar.Solver

class WrappingCalculator : Solver {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val dimensions = input.map { Package.forString(it) }
        return dimensions.map { it.paperRequired() }.sum() to
                dimensions.map { it.ribbonRequired() }.sum()
    }
}

data class Package(
    val lowDim: Int,
    val midDim: Int,
    val highDim: Int,
) {
    fun paperRequired() = 3 * lowDim * midDim + 2 * (lowDim * highDim + midDim * highDim)
    fun ribbonRequired() = 2 * (lowDim + midDim) + lowDim * midDim * highDim

    companion object {
        fun forString(str: String): Package {
            val (low, mid, high) = str.trim().split("x").map { it.toInt() }.sorted()
            return Package(low, mid, high)
        }
    }
}