package com.bartenkelaar.year2020.luggage

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank

class LuggageChecker : Solver {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val rules = input.nonBlank().mapNotNull(LuggageRule::forLine)
        val color = Color("shiny", "gold")

        fun countAllFor(colorBags: Set<Color>, colorsFound: Set<Color>): Int {
            val options = rules.filter { rule -> colorBags.any { bag -> rule.allows(bag) } }
                .map { it.color }
                .toSet()

            return if (colorsFound.containsAll(options)) colorsFound.size
            else countAllFor(options - colorsFound, colorsFound + options)
        }

        return countAllFor(setOf(color), emptySet()) to rules.countBagsIn(color)
    }
}

private fun List<LuggageRule>.countBagsIn(color: Color): Int =
    filter { it.color == color }
        .flatMap { it.conditions }
        .map { it.amount * (1 + countBagsIn(it.color)) }
        .sum()


