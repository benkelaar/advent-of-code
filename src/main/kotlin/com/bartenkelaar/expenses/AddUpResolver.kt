package com.bartenkelaar.expenses

import java.io.File

class AddUpResolver(private val sumChecker: SumChecker) {
    fun resolve(expenses: List<Int>): Int {
        val (expense1, expense2) = checkNotNull(sumChecker.findPairSum(expenses)) { "Couldn't add up expenses tot 2020" }
        return expense1 * expense2
    }
}

fun main() {
    val expenses = File("/Users/benkelaar/Workspaces/personal/advent-of-code/src/main/resources/input/day1.txt")
        .useLines { it.map { line -> line.trim().toInt() }.toList() }

    val resolver = AddUpResolver(SumChecker())

    print(resolver.resolve(expenses))
}
