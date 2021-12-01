package com.bartenkelaar.year2020.boarding

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class TicketTranslator : Solver() {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val ruleLines = input.filter { it.isRuleLine() }
        val rules = ruleLines.nonBlank().map(Rule::fromLine)

        val ticket = input[rules.lastIndex + 3].toTicket()
        val nearbyTickets = input.slice(rules.size + 5..input.lastIndex)
            .nonBlank()
            .map { it.toTicket() }
        val invalidNumbers = nearbyTickets.flatMap { otherTicket ->
            otherTicket.filter { number -> rules.none { rule -> rule.matches(number) } } }

        val validTickets = nearbyTickets.filter { otherTicket ->
            otherTicket.all { number -> rules.any { rule -> rule.matches(number) } } }
        val solvedRules = solveRules(validTickets, rules)
        val departureFields = solvedRules.filterKeys { it.name.startsWith("departure") }.values

        val departureProduct = departureFields.map { ticket[it].toLong() }.reduce(Long::times)
        return invalidNumbers.sum() to departureProduct
    }

    private fun solveRules(validTickets: List<List<Int>>, rules: List<Rule>): Map<Rule, Int> {
        val indexedValues = (0..validTickets.first().lastIndex).map { i -> validTickets.map { it[i] } }
        val indexMatchingRules =
            indexedValues.map { values -> rules.filter { rule -> values.all { rule.matches(it) } } }
                .mapIndexed { i, applicableRules -> applicableRules to i }
                .toMap()

        val initialSelected = indexMatchingRules.findSelected()
        val ambiguous = indexMatchingRules.filterValues { it !in initialSelected.values }
        val selected = selectRules(ambiguous, initialSelected)

        return selected
    }

    private fun Map<List<Rule>, Int>.findSelected() =
        filterKeys { it.count() == 1 }.map { (k, v) -> k.first() to v }.toMap()

    private fun selectRules(ambiguous: Map<List<Rule>, Int>, selected: Map<Rule, Int>): Map<Rule, Int> {
        if (ambiguous.isEmpty()) return selected
        val newAmbiguous = ambiguous.map { (k, v) -> (k - selected.keys) to v }.toMap()
        return selectRules(newAmbiguous.filterKeys { it.count() > 1 }, selected + newAmbiguous.findSelected())
    }

    private fun String.toTicket() = split(",").map { it.toInt() }

    private fun String.isRuleLine() =
        isNotBlank() && !startsWith("your") && !startsWith("nearby") && first().isLetter()
}

data class Rule(
    val name: String,
    val range1: IntRange,
    val range2: IntRange,
) {
    fun matches(number: Int) = number in range1 || number in range2

    companion object {
        fun fromLine(line: String): Rule {
            val (name, int1, int2, int3, int4) = line.split(": ", "-", " or ")
            return Rule(name, int1.toInt()..int2.toInt(), int3.toInt()..int4.toInt())
        }
    }
}