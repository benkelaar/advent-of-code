package com.bartenkelaar.year2020.comunication

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank

class MessageMatcher : Solver {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val unparsedRules = input.nonBlank().filter { it.first().isDigit() }.map { it.toRulePair() }.toMap()
        val messages = input.nonBlank().filter { it.first().isLetter() }
        val ruleZero = Rule.parse(unparsedRules.getValue(0), unparsedRules)
        val matches = ruleZero.matches()
        return messages.count(matches::contains) to 0
    }

    private fun String.toRulePair(): Pair<Int, String> {
        val (no, rule) = split(": ")
        return no.toInt() to rule
    }
}

sealed class Rule {
    abstract fun matches(): Set<String>

    companion object {
        fun parse(ruleString: String, unparsedRules: Map<Int, String>): Rule =
            when {
                ruleString.startsWith("\"") -> Match(ruleString.trim('"'))
                ruleString.contains('|') -> DoubleRule(
                    ruleString.split(" | ").map { parse(it, unparsedRules) })
                ruleString.first().isDigit() -> CompoundRule(
                    ruleString.split(" ")
                        .map { it.toInt() }
                        .map(unparsedRules::getValue)
                        .map { parse(it, unparsedRules) })
                else -> throw IllegalStateException("Unknown rule: $ruleString")
            }

    }
}

data class DoubleRule(private val either: Rule, private val or: Rule) : Rule() {
    constructor(rules: List<Rule>) : this(rules.first(), rules[1])

    override fun matches() = either.matches() + or.matches()
}

data class CompoundRule(private val rules: List<Rule>) : Rule() {
    override fun matches() =
        rules.map { it.matches() }.reduce { left, right -> left.flatMap { l -> right.map { r -> l + r } }.toSet() }
}

data class Match(private val string: String) : Rule() {
    override fun matches() = setOf(string)
}