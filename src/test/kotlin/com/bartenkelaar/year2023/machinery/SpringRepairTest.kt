package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.year2023.gaming.CubeConundrum
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
            "..????##?????.????? 6,2,1",
        ))

        assertEquals(67, a)
    }
}