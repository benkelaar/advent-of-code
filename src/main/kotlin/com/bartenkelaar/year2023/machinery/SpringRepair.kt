package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.of
import com.bartenkelaar.util.tail

class SpringRepair : Solver() {
    private val dotRegex = """\.+""".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val springsCorrections = input.nonBlank().map { it.split(" ") }
            .map { (springs, corrections) -> springs to corrections.split(",").map { it.toInt() } }
        return springsCorrections.sumOfVariations() to springsCorrections.map { (springs, corrections) ->
            (5 of springs).joinToString("?") to corrections + corrections + corrections + corrections + corrections
        }.sumOfVariations()
    }

    private fun List<Pair<String, List<Int>>>.sumOfVariations() =
            sumOf { (springs, corrections) -> countVariations(springs.split(dotRegex).nonBlank(), corrections) }

    private fun countVariations(
        groups: List<String>,
        corrections: List<Int>,
        cache: MutableMap<Pair<List<String>, List<Int>>, Long> = mutableMapOf()
    ) : Long {
        if ((groups to corrections) in cache) return cache[groups to corrections]!!
        if (corrections.isEmpty()) return 0L.takeIf { groups.any { it.contains('#') } } ?: 1L
        if (groups.isEmpty()) return 0L
        val group = groups.first()
        val match = """[?#]{${corrections.first()}}(?=$|\?)""".toRegex().find(group)
            ?: return if (group.contains("#")) 0 else countVariations(groups.tail(), corrections, cache)
        if (group.substring(0, match.range.first).contains("#")) return 0L
        val variations = countVariations(
            listOf(group.substring((match.range.last + 2).coerceAtMost(group.length))) + groups.tail(),
            corrections.tail(), cache
        ) + if (match.value.first() == '?') countVariations(
                groups = listOf(group.substring(match.range.first + 1)) + groups.tail(),
                corrections = corrections,
                cache = cache
            ) else 0L
        cache[groups to corrections] = variations
        return variations
    }
}