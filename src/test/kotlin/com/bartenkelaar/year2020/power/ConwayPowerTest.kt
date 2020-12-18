package com.bartenkelaar.year2020.power

import kotlin.test.Test
import kotlin.test.assertEquals

class ConwayPowerSourceTest {
    @Test
    fun `test creation`() {
        val input = listOf(listOf('.', '#', '.'), listOf('.', '.', '#'), listOf('#', '.', '.'))

        val result = ConwayPowerSource.forInput(input)

        val expected = ConwayPowerSource(
            setOf(CubeCoordinate(1, 0, 0, 0), CubeCoordinate(2, 1, 0, 0), CubeCoordinate(0, 2, 0, 0))
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given input, expect second generation`() {
        val source = ConwayPowerSource.forInput(listOf(listOf('.', '#', '.'), listOf('.', '.', '#'), listOf('#', '#', '#')))

        val result = source.cycle()

        assertEquals(29, result.activeCount())
    }
}

class CubeCoordinateTest {
    @Test
    fun `given coordinate, find neighbours properly`() {
        val coordinate = CubeCoordinate(1, 2, 3, 0)

        val neighbours = coordinate.findNeighbours()

        assertEquals(80, neighbours.size)
    }
}