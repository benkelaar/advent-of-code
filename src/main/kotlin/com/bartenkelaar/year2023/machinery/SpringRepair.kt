package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.tail

class SpringRepair : Solver() {
    private val dotRegex = """\.+""".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val data = input.nonBlank()
            .map { it.split(" ") }
            .map { (springs, corrections) -> springs.split(dotRegex).nonBlank() to corrections.split(",").map { it.toInt() } }
        return data.sumOf { (groups, corrections) ->
            val varis = countVariations(groups, corrections)
            varis
        } to 0
    }

    private fun countVariations(groups: List<String>, corrections: List<Int>) : Int {
        if (corrections.isEmpty()) return 0.takeIf { groups.any { it.contains('#') } } ?: 1
        if (groups.isEmpty()) return 0
        val group = groups.first()
        val match = """[?#]{${corrections.first()}}(?=$|\?)""".toRegex().find(group)
            ?: return countVariations(groups.tail(), corrections)
        if (group.substring(0, match.range.first).contains("#")) return 0
        return countVariations(
            listOf(group.substring((match.range.last + 2).coerceAtMost(group.length))) + groups.tail(), corrections.tail()) +
                if (match.value.first() == '?') countVariations(listOf(group.substring(match.range.first + 1)) + groups.tail(), corrections) else 0
    }
}