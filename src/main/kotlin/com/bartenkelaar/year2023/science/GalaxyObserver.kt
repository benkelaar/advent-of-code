package com.bartenkelaar.year2023.science

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.tail
import kotlin.math.max
import kotlin.math.min

class GalaxyObserver : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val emptyRows = input.mapIndexedNotNull { i, row -> i.takeIf { !row.contains('#') } }.toSet()
        val emptyColumns = input.first().indices.filter { i -> input.nonBlank().all { row -> row[i] == '.' } }.toSet()
        val galaxyCoordinates = input.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, c -> x.takeIf { c == '#' }?.let { Coordinate(x, y) } }
        }
        return sumDistances(galaxyCoordinates, emptyRows, emptyColumns) to 0
    }

    private tailrec fun sumDistances(coordinates: List<Coordinate>, emptyRows: Set<Int>, emptyColumns: Set<Int>, sum: Long = 0): Long {
        if (coordinates.isEmpty()) return sum
        return sumDistances(coordinates.tail(), emptyRows, emptyColumns, sum + coordinates.tail()
            .sumOf { expandedDistance(it, coordinates.first(), emptyRows, emptyColumns) })
    }

    private fun expandedDistance(c1: Coordinate, c2: Coordinate, emptyRows: Set<Int>, emptyColumns: Set<Int>): Long {
        return c1.distanceTo(c2).toLong() + 999_999 * emptyColumns.count { it > min(c1.x, c2.x) && it < max(c1.x, c2.x) } + 999_999 * emptyRows.count { it > min(c1.y, c2.y) && it < max(c1.y, c2.y) }
    }
}