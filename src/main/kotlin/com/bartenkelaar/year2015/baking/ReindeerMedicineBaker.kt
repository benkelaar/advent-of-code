package com.bartenkelaar.year2015.baking

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import java.util.*

private data class Medicine(val molecule: String, val matchIndex: Int, val replacements: Int): Comparable<Medicine> {
    fun matchScore() = matchIndex - replacements

    override fun compareTo(other: Medicine) = other.matchScore().compareTo(matchScore())
}

class ReindeerMedicineBaker : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val replacements = input.filter { "=>" in it }
            .map { it.split(" => ") }
            .groupBy { it.first() }
            .mapValues { (_, rules) -> rules.map { it[1] } }
        val molecule = input.nonBlank().last()

        val uniqueMolecules = replacements.applyTo(molecule).size

        val replacementsNecessary = replacements.searchFor(molecule)

        return uniqueMolecules to replacementsNecessary
    }

    private fun Map<String, List<String>>.applyTo(molecule: String) =
        flatMap { (f, ts) ->
            ts.flatMap { t ->
                f.toRegex().findAll(molecule).map { molecule.replaceRange(it.range, t) }
            }
        }.toSet()

    private fun Map<String, List<String>>.searchFor(molecule: String): Int {
        val initial = Medicine("e", -1, 0)
        val queue = PriorityQueue<Medicine>()
        queue.offer(initial)

        while (true) {
            val (attempt, matchIndex, replacements) = queue.poll()
            if (attempt == molecule) return replacements
            val matching = attempt.substring(0..matchIndex)
            val faulty = attempt.substring(matchIndex + 1)
            val possibleReplacements = filterKeys { k -> k in faulty }
            possibleReplacements.applyTo(faulty)
                .map { matching + it }
                .map { Medicine(it, molecule.findMatchIndex(it), replacements + 1) }
                .forEach(queue::offer)
        }
        return -1
    }
}

private fun String.findMatchIndex(match: String): Int {
    var matchIndex = -1
    while (matchIndex != lastIndex && get(matchIndex + 1) == match[matchIndex + 1]) matchIndex++
    return matchIndex
}
