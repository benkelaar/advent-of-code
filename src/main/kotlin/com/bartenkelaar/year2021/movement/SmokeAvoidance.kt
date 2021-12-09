package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.get
import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

data class Basin(val vent: Vent, val coordinates: Set<Coordinate>) {
    val size get() = coordinates.size
}

data class Vent(val coordinate: Coordinate, val height: Int) {
    constructor(x: Int, y: Int, height: Int) : this(Coordinate(x, y), height)

    val strength get() = height + 1
}

class SmokeAvoidance : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val heightMap = input.nonBlank().map { row -> row.split("").nonBlank().map { it.toInt() } }
        val columnLength = heightMap.size
        val rowLength = heightMap.first().size

        fun Int.isVent(x: Int, y: Int) = getNeighbours(x, y, rowLength, columnLength).all { c -> heightMap[c] > this }

        val vents = heightMap.flatMapIndexed { i, row ->
            row.mapIndexedNotNull { j, h -> h.takeIf { it.isVent(j, i) }?.let { Vent(j, i, h) } }
        }

        val basins = vents.map { vent -> heightMap.findBasin(vent, columnLength, rowLength) }
        val sortedBasins = basins.sortedByDescending { it.size }
        return vents.sumOf { it.strength } to sortedBasins[0].size * sortedBasins[1].size * sortedBasins[2].size
    }

    private fun getNeighbours(x: Int, y: Int, rowLength: Int, columnLength: Int) =
        listOf(-1 to 0, 1 to 0, 0 to 1, 0 to -1)
            .map { (dx, dy) -> Coordinate(x, y).transpose(dx, dy) }
            .filter { it.isInRange(0, rowLength - 1, y2 = columnLength - 1) }

    private fun Coordinate.getNeighbours(rowLength: Int, columnLength: Int) =
        getNeighbours(x, y, rowLength, columnLength)

    private fun List<List<Int>>.findBasin(vent: Vent, columnLength: Int, rowLength: Int): Basin {
        tailrec fun recursiveFloodFill(height: Int, coordinates: Set<Coordinate>): Set<Coordinate> =
            if (height == 9) coordinates else recursiveFloodFill(
                height = height + 1,
                coordinates = coordinates + coordinates.flatMap { coordinate ->
                    coordinate.getNeighbours(rowLength, columnLength).filter { this[it] == height }
                }
            )

        return Basin(vent, recursiveFloodFill(vent.height, setOf(vent.coordinate)))
    }
}
