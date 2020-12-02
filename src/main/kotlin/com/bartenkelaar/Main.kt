package com.bartenkelaar

import com.bartenkelaar.expenses.AddUpResolver
import com.bartenkelaar.expenses.SumChecker
import com.bartenkelaar.passwords.PasswordChecker

interface Solver {
    fun solve(input: List<String>): Pair<Int, Int>
}

private val solvers = listOf(
    AddUpResolver(SumChecker()),
    PasswordChecker()
)

fun main() {
    solvers.forEachIndexed { i, solver ->
        val input = solver.javaClass.getResource("/input/day${i + 1}.txt").readText().lines()
        println("Day $i: ${solver.solve(input)}")
    }
}
