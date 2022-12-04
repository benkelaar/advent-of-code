package com.bartenkelaar.year2022.calories

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.zipPerEmpty

class CalorieCounter : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val elfCaloriesPacked = input.zipPerEmpty().map { calories -> calories.sumOf { it.toInt() } }
        return elfCaloriesPacked.maxOrNull()!! to elfCaloriesPacked.sortedDescending().slice(0..2).sum()
    }
}