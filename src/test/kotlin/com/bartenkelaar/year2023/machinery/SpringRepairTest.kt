package com.bartenkelaar.year2023.machinery

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SpringRepairTest {
    private val conundrum = SpringRepair()

    @Test
    fun `the example should work`() {
        val (a, _) = conundrum.solve(listOf(
            "???.### 1,1,3",
            ".??..??...?##. 1,1,3",
            "?#?#?#?#?#?#?#? 1,3,1,6",
            "????.#...#... 4,1,1",
            "????.######..#####. 1,6,5",
            "?###???????? 3,2,1",
            "#.??#?#????? 1,2,1,1",
        ))

        assertEquals(25, a)
    }

    @Test
    fun `test skipping of required signs`() {
        val (a, _) = conundrum.solve(listOf(
            "??#.## 1,2",
        ))

        assertEquals(1, a)
    }
}