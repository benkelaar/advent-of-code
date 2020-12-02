package com.bartenkelaar.expenses

import com.bartenkelaar.Solver

class AddUpResolver(private val sumChecker: SumChecker) : Solver<Pair<Int, Int>> {
    override fun solve(input: List<String>) = resolve(input.map { it.trim().toInt() }.toSet())

    fun resolve(expenses: Set<Int>): Pair<Int, Int> {
        val (pair1, pair2) = checkNotNull(sumChecker.findPairSum(expenses)) { "Couldn't add up expenses tot 2020" }
        val (triple1, triple2, triple3) = sumChecker.findTripleSum(expenses)
        return pair1 * pair2 to triple1 * triple2 * triple3
    }
}
