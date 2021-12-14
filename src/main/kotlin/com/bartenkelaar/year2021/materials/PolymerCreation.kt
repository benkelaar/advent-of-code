package com.bartenkelaar.year2021.materials

import com.bartenkelaar.util.Solver

class PolymerCreation : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val start = input.first()
        val insertionRules = input.filter { "-" in it }
            .map { it.split(" -> ") }
            .associate { (f, t) -> f to t }

        val polymer = (1..10).fold(start) { polymer, _ -> polymer.expand(insertionRules) }
        val trivialCounts = polymer.toSet().map { c -> polymer.count { it == c } }

        val pairLinks = insertionRules.mapValues { (f, t) -> listOf(f.first().toString() + t, t + f.last()) }
        val startPairs = start.zipWithNext().map { "${it.first}${it.second}" }
        val startPairCount = startPairs.groupBy { it }.mapValues { (p, ps) -> ps.size.toLong() }
        val lastLetter = start.last()
        val letterCounts = (1..40).fold(startPairCount) { pairs, _ -> pairLinks.newPairCount(pairs) }
            .map { (p, c) -> p.first() to c }
            .sumSeconds()
            .mapValues { (k, v) -> if (k == lastLetter) v + 1 else v }

        val maxCount = letterCounts.maxOf { (_, v) -> v} - letterCounts.minOf { (_, v) -> v}
        return trivialCounts.maxOrNull()!! - trivialCounts.minOrNull()!! to maxCount
    }

    private fun Map<String, List<String>>.newPairCount(pairs: Map<String, Long>) =
        pairs.flatMap { (p, c) -> this[p]?.let { it.map { np -> np to c } } ?: listOf() }.sumSeconds()

    private fun String.expand(insertionRules: Map<String, String>) = zipWithNext()
        .joinToString("") { (c1, c2) -> insertionRules["$c1$c2"]?.let { c1.toString() + it } ?: c1.toString() } + last()

    private fun <T> List<Pair<T, Long>>.sumSeconds() = groupBy { it.first }.mapValues { (_, cs) -> cs.sumOf { it.second } }
}


