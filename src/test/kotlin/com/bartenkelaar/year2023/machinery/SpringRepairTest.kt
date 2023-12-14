package com.bartenkelaar.year2023.machinery

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SpringRepairTest {
    private val springRepair = SpringRepair()

    @Test
    fun `the example should work`() {
        val (a, b) = springRepair.solve(listOf(
            "???.### 1,1,3",
            ".??..??...?##. 1,1,3",
            "?#?#?#?#?#?#?#? 1,3,1,6",
            "????.#...#... 4,1,1",
            "????.######..#####. 1,6,5",
            "?###???????? 3,2,1",
        ))

        assertEquals(21L, a)
        assertEquals(525152L, b)
    }

    @Test
    fun `test skipping of required signs`() {
        val (a, _) = springRepair.solve(listOf(
            "??#.## 1,2",
        ))

        assertEquals(1L, a)
    }


    @Test
    fun `test quintuple failure`() {
        val (_, b) = springRepair.solve(listOf(
            "?###???????? 3,2,1",
        ))

        assertEquals(506250L, b)
    }
}