package com.bartenkelaar.year2020.comunication

import kotlin.test.Test
import kotlin.test.assertEquals

class RuleTest {
    private val unparsedExampleRules = mapOf(
        0 to "4 1 5",
        1 to "2 3 | 3 2",
        2 to "4 4 | 5 5",
        3 to "4 5 | 5 4",
        4 to "\"a\"",
        5 to "\"b\"",
    )

    @Test
    fun `given example, expect correct parsing`() {
        val ruleZero = Rule.parse(0, "4 1 5", unparsedExampleRules, false)

        val rule4 = Match("a")
        val rule5 = Match("b")
        val rule3 = DoubleRule(CompoundRule(listOf(rule4, rule5)), CompoundRule(listOf(rule5, rule4)))
        val rule2 = DoubleRule(CompoundRule(listOf(rule4, rule4)), CompoundRule(listOf(rule5, rule5)))
        val rule1 = DoubleRule(CompoundRule(listOf(rule2, rule3)), CompoundRule(listOf(rule3, rule2)))
        assertEquals(CompoundRule(listOf(rule4, rule1, rule5)), ruleZero)
    }
    @Test
    fun `given example, expect correct matching`() {
        val ruleZero = Rule.parse(0, "4 1 5", unparsedExampleRules, false)
        val messages = listOf(
            "ababbb",
            "bababa",
            "abbbab",
            "aaabbb",
            "aaaabbb",
        )

        assertEquals(2, messages.count(ruleZero::matchesCompletely))
    }
}

class Rule8Test {
    @Test
    fun `given simple match, expect them matched`() {
        val rule8 = Rule8(Match("c"))

        val actual = rule8.matches("c")

        assertEquals(true to 1, actual)
    }

    @Test
    fun `given repeating match, expect them matched`() {
        val rule = CompoundRule(listOf(Rule8(Match("c"))))

        val actual = rule.matches("ccc")

        assertEquals(true to 3, actual)
    }

    @Test
    fun `given uneager match, expect them matched`() {
        val rule = CompoundRule(listOf(Rule8(Match("c")), Match("c")))

        val actual = rule.matches("cc")

        assertEquals(true to 2, actual)
    }

    @Test
    fun `given complex uneager match, expect them matched`() {
        val rule = CompoundRule(listOf(Match("a"), Rule8(DoubleRule(Match("c"), Match("d"))), CompoundRule(listOf(Match("d"), Match("e")))))

        val actual = rule.matches("acdcdcde")

        assertEquals(true to 8, actual)
    }
}

class Rule11Test {
    @Test
    fun `given simple match, expect them matched`() {
        val rule = Rule11(Match("d"), Match("f"))

        val actual = rule.matches("df")

        assertEquals(true to 2, actual)
    }

    @Test
    fun `given repeating match, expect them matched`() {
        val rule = Rule11(Match("d"), Match("f"))

        val actual = rule.matches("ddddffff")

        assertEquals(true to 8, actual)
    }

    @Test
    fun `given uneager match, expect them matched`() {
        val rule = CompoundRule(listOf(Rule11(Match("d"), Match("f")), Match("f")))

        val actual = rule.matches("ddfff")

        assertEquals(true to 5, actual)
    }
}