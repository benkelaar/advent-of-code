package com.bartenkelaar.year2020.comunication

import kotlin.test.Test
import kotlin.test.assertEquals

class MessageMatcherTest {
    val matcher = MessageMatcher()

    val complexRules = mapOf(
        42 to "9 14 | 10 1",
        9 to "14 27 | 1 26",
        10 to "23 14 | 28 1",
        1 to "\"a\"",
        11 to "42 31",
        5 to "1 14 | 15 1",
        19 to "14 1 | 14 14",
        12 to "24 14 | 19 1",
        16 to "15 1 | 14 14",
        31 to "14 17 | 1 13",
        6 to "14 14 | 1 14",
        2 to "1 24 | 14 4",
        0 to "8 11",
        13 to "14 3 | 1 12",
        15 to "1 | 14",
        17 to "14 2 | 1 7",
        23 to "25 1 | 22 14",
        28 to "16 1",
        4 to "1 1",
        20 to "14 14 | 1 15",
        3 to "5 14 | 16 1",
        27 to "1 6 | 14 18",
        14 to "\"b\"",
        21 to "14 1 | 1 14",
        25 to "1 1 | 1 14",
        22 to "14 14",
        8 to "42",
        26 to "14 22 | 1 20",
        18 to "15 15",
        7 to "14 5 | 1 21",
        24 to "14 1",
    )

    @Test
    fun `given example input, expect output`() {
        val input = """
            |42: 9 14 | 10 1
            |9: 14 27 | 1 26
            |10: 23 14 | 28 1
            |1: "a"
            |11: 42 31
            |5: 1 14 | 15 1
            |19: 14 1 | 14 14
            |12: 24 14 | 19 1
            |16: 15 1 | 14 14
            |31: 14 17 | 1 13
            |6: 14 14 | 1 14
            |2: 1 24 | 14 4
            |0: 8 11
            |13: 14 3 | 1 12
            |15: 1 | 14
            |17: 14 2 | 1 7
            |23: 25 1 | 22 14
            |28: 16 1
            |4: 1 1
            |20: 14 14 | 1 15
            |3: 5 14 | 16 1
            |27: 1 6 | 14 18
            |14: "b"
            |21: 14 1 | 1 14
            |25: 1 1 | 1 14
            |22: 14 14
            |8: 42
            |26: 14 22 | 1 20
            |18: 15 15
            |7: 14 5 | 1 21
            |24: 14 1
            |
            |abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
            |bbabbbbaabaabba
            |babbbbaabbbbbabbbbbbaabaaabaaa
            |aaabbbbbbaaaabaababaabababbabaaabbababababaaa
            |bbbbbbbaaaabbbbaaabbabaaa
            |bbbababbbbaaaaaaaabbababaaababaabab
            |ababaaaaaabaaab
            |ababaaaaabbbaba
            |baabbaaaabbaaaababbaababb
            |abbbbabbbbaaaababbbbbbaaaababb
            |aaaaabbaabaaaaababaa
            |aaaabbaaaabbaaa
            |aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
            |babaaabbbaaabaababbaabababaaab
            |aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
            """.trimMargin().split("\n")

        val answer = matcher.solve(input)

        assertEquals(3 to 12, answer)
    }

    @Test
    fun `given matches for both, expect both match`() {
        val loopingRuleZero = Rule.parse(0, "8 11", complexRules, true)
        val ruleZero = Rule.parse(0, "8 11", complexRules, false)

        val answer1 = ruleZero.matches("bbabbbbaabaabba")
        val answer2 = ruleZero.matches("ababaaaaaabaaab")
        val answer3 = ruleZero.matches("ababaaaaabbbaba")

        val loopingAnswer1 = loopingRuleZero.matches("bbabbbbaabaabba")
        val loopingAnswer2 = loopingRuleZero.matches("ababaaaaaabaaab")
        val loopingAnswer3 = loopingRuleZero.matches("ababaaaaabbbaba")

        assertEquals(true to 15, answer1)
        assertEquals(true to 15, answer2)
        assertEquals(true to 15, answer3)

        assertEquals(true to 15, loopingAnswer1)
        assertEquals(true to 15, loopingAnswer2)
        assertEquals(true to 15, loopingAnswer3)
    }

    @Test
    fun `given example zero rule, expect output`() {
        val ruleZero = Rule.parse(0, "8 11", complexRules, true)

        val answer = ruleZero.matches("babbbbaabbbbbabbbbbbaabaaabaaa")

        assertEquals(true to 30, answer)
    }
}

