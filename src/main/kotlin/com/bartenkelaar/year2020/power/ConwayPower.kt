package com.bartenkelaar.year2020.power

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.sign

class ConwayPower : Solver {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val cubes = input.nonBlank().map { it.toCharArray().toList() }
        println(cubes)
        var state = listOf(cubes)
        for (i in 1..6) {
            state = state.grow()
            state = state.nextRound()
            println(state.size)
        }

        return state.map { plane -> plane.map { row -> row.count { c -> c == '#' } }.sum() }.sum() to 0
    }

    private fun List<List<List<Char>>>.nextRound() = mapIndexed { z, plane ->
        plane.mapIndexed { y, row ->
            row.mapIndexed { x, seat ->
                val neighbours = findNeighbours(x, y, z)
                when (neighbours.count { it == '#' }) {
                    3 -> '#'
                    2 -> seat
                    else -> '.'
                }
            }
        }
    }

    private fun List<List<List<Char>>>.findNeighbours(x: Int, y: Int, z: Int): List<Char> {
        val xRange = max(x - 1, 0)..min(x + 1, this[1][1].lastIndex)
        val yRange = max(y - 1, 0)..min(y + 1, this[1].lastIndex)
        val zRange = max(z - 1, 0)..min(z + 1, lastIndex)
        return zRange.flatMap { this[it].findAll(xRange, yRange, it == z) }
    }
}

private fun List<List<Char>>.findAll(xRange: IntRange, yRange: IntRange, skipSelf: Boolean): List<Char> {
    val coordinates = xRange.flatMap { x -> yRange.map { y -> x to y } }.toMutableList()
    if (skipSelf) coordinates.remove(0 to 0)
    return coordinates.map { (x, y) -> this[y][x] }
}

private fun List<List<List<Char>>>.grow(): List<List<List<Char>>> {
    val basePlane = first()
    val baseRow = basePlane.first()
    val emptyRow = (0..baseRow.lastIndex + 2).map { '.' }
    val emptyPlane = (0..basePlane.lastIndex + 2).map { emptyRow }
    return listOf(
        emptyPlane,
        *map { plane -> listOf(
            emptyRow,
            *plane.map { row -> listOf('.', *row.toTypedArray(), '.') }.toTypedArray(),
            emptyRow)
        }.toTypedArray(),
        emptyPlane
    )
}
