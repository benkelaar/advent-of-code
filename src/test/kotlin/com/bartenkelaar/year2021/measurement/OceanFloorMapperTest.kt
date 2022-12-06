package com.bartenkelaar.year2021.measurement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OceanFloorMapperTest {
    private val mapper = OceanFloorMapper()

    @Test
    fun `example`() {
        val input = listOf("..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
                "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
                ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
                ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
                ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
                "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
                "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#",
                "",
                "#..#.",
                "#....",
                "##..#",
                "..#..",
                "..###")

        val result = mapper.solve(input)

        assertEquals(35, result.first)
    }
}