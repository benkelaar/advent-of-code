package com.bartenkelaar.year2020.expenses

import com.bartenkelaar.Solver

class AddUpResolver(private val sumChecker: SumChecker) : Solver {
    override fun solve(input: List<String>) = resolve(input.map { it.trim().toLong() }.toSet())

    fun resolve(expenses: Set<Long>): Pair<Long, Long> {
        val (pair1, pair2) = checkNotNull(sumChecker.findPairSum(expenses)) { "Couldn't add up expenses tot 2020" }
        val (triple1, triple2, triple3) = sumChecker.findTripleSum(expenses)
        return pair1 * pair2 to triple1 * triple2 * triple3
    }
}
