package com.bartenkelaar.expenses

import java.io.File

class AddUpResolver(private val sumChecker: SumChecker) {
    fun resolve(expenses: Set<Int>): Pair<Int, Int> {
        val (pair1, pair2) = checkNotNull(sumChecker.findPairSum(expenses)) { "Couldn't add up expenses tot 2020" }
        val (triple1, triple2, triple3) = sumChecker.findTripleSum(expenses)
        return pair1 * pair2 to triple1 * triple2 * triple3
    }
}

fun main() {
    val expenses = AddUpResolver::class.java.getResource("/input/day1.txt")
        .readText().lines().map { line -> line.trim().toInt() }.toSet()

    val resolver = AddUpResolver(SumChecker())
    print(resolver.resolve(expenses))
}
