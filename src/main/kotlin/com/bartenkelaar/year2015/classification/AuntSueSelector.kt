package com.bartenkelaar.year2015.classification

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class AuntSueSelector : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val simpleRules = mapOf(
            "children" to 3,
            "cats" to 7,
            "samoyeds" to 2,
            "pomeranians" to 3,
            "akitas" to 0,
            "vizslas" to 0,
            "goldfish" to 5,
            "trees" to 3,
            "cars" to 2,
            "perfumes" to 1,
        )

        val sues = input.nonBlank().map {
            it.replace("""Sue \d+: """.toRegex(), "").split(", ")
                .associate { attribute -> attribute.split(": ").let { (name, amount) -> name to amount.toInt() } }
        }
        val simpleSueIndex = sues.indexOfFirst { it.all { (attribute, amount) -> simpleRules[attribute] == amount } }

        val complexRules = simpleRules.mapValues { (k, v) -> when (k) {
            in listOf("cats", "trees") -> { t: Int -> t > v }
            in listOf("goldfish", "pomeranians") -> { t -> t < v }
            else -> { t -> t == v }
        } }
        val complexSueIndex = sues.indexOfFirst { it.all { (attribute, amount) -> complexRules.getValue(attribute)(amount) } }

        return simpleSueIndex + 1 to complexSueIndex + 1
    }


}
