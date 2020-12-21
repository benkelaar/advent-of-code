package com.bartenkelaar.year2020.comunication

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.only

class AllergenFinder : Solver {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val information = input.nonBlank()
            .map { it.split(" (contains ") }
            .map { it[1].trim(')').split(", ") to it.first().split(" ") }

        val allergenOptions = mutableMapOf<String, Set<String>>()
        information.forEach { (resolvedAllergens, ingredients) ->
            resolvedAllergens.forEach {
                val ingredientSet = ingredients.toSet()
                if (it in allergenOptions) allergenOptions[it] = allergenOptions.getValue(it).intersect(ingredientSet)
                else allergenOptions[it] = ingredientSet
            }
        }

        val allIngredients = information.flatMap { it.second }
        val otherIngredientCount = (allIngredients - allergenOptions.values.flatten()).size

        val determinedAllergens = downSelectAllergens(allergenOptions, mutableMapOf())

        return otherIngredientCount to determinedAllergens.toList()
            .sortedBy { it.first }
            .joinToString(",") { it.second }
    }

    private fun downSelectAllergens(allergenOptions: Map<String, Set<String>>, selection: Map<String, String>): Map<String, String> {
        if (allergenOptions.isEmpty()) return selection
        val remainingOptions = (allergenOptions - selection.keys).mapValues { (_, v) -> v - selection.values }
        val newSelection = selection + allergenOptions.filterValues { it.size == 1 }.mapValues { (_, v) -> v.only() }
        return downSelectAllergens(remainingOptions, newSelection)
    }
}