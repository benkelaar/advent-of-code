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

    @Test
    fun `given overlapping monster, find it`() {
        assertEquals(2, listOf(
            "..................#...............#.",
            "#....##....##...####.##....##....###",
            ".#..#..#..#..#..##..#..#..#..#..#...",
        ).countMonsters())
    }

    @Test
    fun `given monster with noise, find it`() {
        assertEquals(1, listOf(
            ".#####..#.###..##.#.",
            "#..####.##.##.##.###",
            ".#..##.#..#..#..#...",
        ).countMonsters())
    }

    @Test
    fun `given multiple monsters with noise, find them`() {
        assertEquals(4, listOf(
            ".#####..#.###..##.#..##..#..#.###..##.#.",
            "#..####.##.##.##.####..####.##.##.##.###",
            ".#..##.#..#..#..#....##.##.#..#..#..#...",
            ".#..##.##.###...#.#..#####..#.###...#.#.",
            "#..####.##.##.#######..####.##.##.##.###",
            ".#..##.#..#..#..#....#..##.#..#..#.##...",
        ).countMonsters())
    }
}