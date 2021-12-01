package com.bartenkelaar.year2020.comunication

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class MessageMatcher : Solver() {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val unparsedRules = input.nonBlank().filter { it.first().isDigit() }.map { it.toRulePair() }.toMap()
        val messages = input.nonBlank().filter { it.first().isLetter() }
        val ruleZero = Rule.parse(0, unparsedRules.getValue(0), unparsedRules, false)
        val loopingRuleZero = Rule.parse(0, unparsedRules.getValue(0), unparsedRules, true)
        val simpleCount = messages.count(ruleZero::matchesCompletely)
        val loopingCount = messages.count(loopingRuleZero::matchesCompletely)
        return simpleCount to loopingCount
    }

    private fun String.toRulePair(): Pair<Int, String> {
        val (no, rule) = split(": ")
        return no.toInt() to rule
    }
}

sealed class Rule {
    abstract fun matches(string: String): Pair<Boolean, Int>

    abstract fun toMatches(): Set<String>

    fun matchesCompletely(string: String): Boolean {
        val (match, consumed) = matches(string)
        return match && consumed == string.length
    }

    companion object {
        fun parse(id: Int, ruleString: String, unparsedRules: Map<Int, String>, loop: Boolean): Rule =
            when {
                loop && id == 0 -> Rule0(unparsedRules.parseBase(42, false), unparsedRules.parseBase(31, false))
                ruleString.startsWith("\"") -> Match(ruleString.trim('"'))
                ruleString.contains('|') -> DoubleRule(
                    ruleString.split(" | ").map { parse(id, it, unparsedRules, loop) })
                ruleString.first().isDigit() -> CompoundRule(
                    ruleString.split(" ")
                        .map(String::toInt)
                        .map { unparsedRules.parseBase(it, loop) })
                else -> throw IllegalStateException("Unknown rule: $ruleString")
            }

        private fun Map<Int, String>.parseBase(id: Int, loop: Boolean) = parse(id, getValue(id), this, loop)
    }
}

data class DoubleRule(private val either: Rule, private val or: Rule) : Rule() {
    constructor(rules: List<Rule>) : this(rules.first(), rules[1])

    override fun matches(string: String): Pair<Boolean, Int> {
        val match1 = either.matches(string)
        val match2 = or.matches(string)
        return if (match1.first) match1 else if (match2.first) match2 else false to 0
    }

    override fun toMatches() = either.toMatches() + or.toMatches()
}

data class CompoundRule(private val rules: List<Rule>) : Rule() {
    override fun matches(string: String): Pair<Boolean, Int> {
        var matches = string.isNotEmpty()
        var i = 0
        var consumed = 0
        while (matches && i < rules.size && consumed < string.length) {
            val (match, c) = rules[i++].matches(string.from(consumed))
            matches = match
            consumed += c
        }
        return if (matches) true to consumed else false to 0
    }

    override fun toMatches() =
        rules.map { it.toMatches() }.reduce { left, right -> left.flatMap { l -> right.map { r -> l + r } }.toSet() }
}

data class Match(private val match: String) : Rule() {
    override fun matches(string: String): Pair<Boolean, Int> {
        val matches = string.startsWith(match)
        return matches to (match.length.takeIf { matches } ?: 0)
    }

    override fun toMatches() = setOf(match)
}

/**
 * Rule zero consumes an X amount of rule 42s and y amount of rule 31s,
 * with the requirements that y < x and the string is fully consumed after.
 */
data class Rule0(val rule42: Rule, val rule31: Rule) : Rule() {
    override fun matches(string: String): Pair<Boolean, Int> {
        val (initialMatch, initialConsumption) = rule42.matches(string)

        if (!initialMatch) return false to 0
        var consumption = initialConsumption
        var firstCount = 1
        while(true) {
            val (nextMatch, nextConsumption) = rule42.matches(string.from(consumption))
            if (nextMatch) {
                firstCount++
                consumption += nextConsumption
                if (rule31.matches(string.from(consumption)).first) break
            } else break
        }

        for (i in 0 until firstCount - 1) {
            val (nextMatch, nextConsumption) = rule31.matches(string.from(consumption))
            if (!nextMatch) return if (i > 0 && consumption == string.length) true to consumption else false to 0
            consumption += nextConsumption
        }

        return if (consumption == string.length) true to consumption else false to 0
    }

    override fun toMatches() = rule42.toMatches()
        .flatMap { l -> rule31.toMatches().map { r -> l + "*(x + y)" + r + "*(x)" } }
        .toSet()
}

private fun String.from(start: Int) = slice(start..lastIndex)
