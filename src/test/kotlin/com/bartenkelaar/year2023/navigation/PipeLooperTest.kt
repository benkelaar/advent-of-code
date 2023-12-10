package com.bartenkelaar.year2023.navigation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PipeLooperTest {
    private val looper = PipeLooper()

    @Test
    fun `The example should work`() {
        val input = listOf(
            "..F7.",
            "-FJ|.",
            "SJ.L7",
            "|F--J",
            "LJ...",

        )

        val (a, b) = looper.solve(input)

        assertEquals(8, a)
        assertEquals(1, b)
    }

    @Test
    fun `The enclosing example should work`() {
        val input = listOf(
            "..........",
            ".S------7.",
            ".|F----7|.",
            ".||OOOO||.",
            ".||OOOO||.",
            ".|L-7F-J|.",
            ".|II||II|.",
            ".L--JL--J.",
            "..........",
        )

        val (_, b) = looper.solve(input)

        assertEquals(4, b)
    }
    @Test
    fun `The bigger example should work`() {
        val input = listOf(
            "FF7FSF7F7F7F7F7F---7",
            "L|LJ||||||||||||F--J",
            "FL-7LJLJ||||||LJL-77",
            "F--JF--7||LJLJIF7FJ-",
            "L---JF-JLJIIIIFJLJJ7",
            "|F|F-JF---7IIIL7L|7|",
            "|FFJF7L7F-JF7IIL---7",
            "7-L-JL7||F7|L7F-7F7|",
            "L.L7LFJ|||||FJL7||LJ",
            "L7JLJL-JLJLJL--JLJ.L",
        )

        val (_, b) = looper.solve(input)

        assertEquals(10, b)
    }
}