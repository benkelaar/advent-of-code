package com.bartenkelaar.year2020.construction

import kotlin.test.Test
import kotlin.test.assertEquals

class FloorPatternBuilderTest {
    private val builder = FloorPatternBuilder()

    @Test
    fun example() {
        val input = listOf(
            "sesenwnenenewseeswwswswwnenewsewsw",
            "neeenesenwnwwswnenewnwwsewnenwseswesw",
            "seswneswswsenwwnwse",
            "nwnwneseeswswnenewneswwnewseswneseene",
            "swweswneswnenwsewnwneneseenw",
            "eesenwseswswnenwswnwnwsewwnwsene",
            "sewnenenenesenwsewnenwwwse",
            "wenwwweseeeweswwwnwwe",
            "wsweesenenewnwwnwsenewsenwwsesesenwne",
            "neeswseenwwswnwswswnw",
            "nenwswwsewswnenenewsenwsenwnesesenew",
            "enewnwewneswsewnwswenweswnenwsenwsw",
            "sweneswneswneneenwnewenewwneswswnese",
            "swwesenesewenwneswnwwneseswwne",
            "enesenwswwswneneswsenwnewswseenwsese",
            "wnwnesenesenenwwnenwsewesewsesesew",
            "nenewswnwewswnenesenwnesewesw",
            "eneswnwswnwsenenwnwnwwseeswneewsenese",
            "neswnwewnwnwseenwseesewsenwsweewe",
            "wseweeenwnesenwwwswnew",
        )

        assertEquals(10 to 2208, builder.solve(input))
    }

    @Test
    fun `given all back to same coordinate`() {
        val input = listOf(
            "eswsw",
            "wsese",
            "sesw",
        )

        assertEquals(1, builder.solve(input).first)
    }

    @Test
    fun `simple pattern`() {
        val input = listOf(
            "w",
            "we",
        )

        assertEquals(1666, builder.solve(input).second)
    }
}

class HexDirectionTest {
//    @Test
//    fun testUnifiedMoves()
}