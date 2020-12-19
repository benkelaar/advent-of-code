package com.bartenkelaar.year2020.comunication

import kotlin.test.Test

class MessageMatcherTest

class RuleTest {
    @Test
    fun `given example, expect 8 matches`() {
        Rule.parse("4 1 5")
        0: 4 1 5
        1: 2 3 | 3 2
        2: 4 4 | 5 5
        3: 4 5 | 5 4
        4: "a"
        5: "b"
    }
}