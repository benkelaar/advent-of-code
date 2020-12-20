package com.bartenkelaar.year2020.comunication

import kotlin.test.Test
import kotlin.test.assertEquals

class CountMonsterTest {
    @Test
    fun `given monster, find it`() {
        assertEquals(1, listOf(
            "..................#.",
            "#....##....##....###",
            ".#..#..#..#..#..#...",
        ).countMonsters())
    }
}