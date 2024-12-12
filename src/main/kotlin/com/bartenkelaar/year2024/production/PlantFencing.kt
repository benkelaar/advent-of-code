package com.bartenkelaar.year2024.production

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver
import com.bartenkelaar.year2024.operation.Direction
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

data class Line(val reference: Int, val side: Direction)

class PlantFencing : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val field = Grid.forChars(input)
        val areas = field.findAreas()

        val fenceCosts = areas.sumOf { it.size * fenceAround(it) }
        val bulkFenceCosts = areas.sumOf { it.size * countSides(it) }

        return fenceCosts to bulkFenceCosts
    }

    private fun Grid<Char>.findAreas(): Set<Set<Coordinate>> {
        val areas = mutableSetOf<Set<Coordinate>>()
        var coordinates = coordinates()
        while (coordinates.isNotEmpty()) {
            val start = coordinates.first()
            val area = flood(get(start), setOf(start))
            areas += area
            coordinates -= area
        }
        return areas.toSet()
    }

    private fun Grid<Char>.flood(type: Char, neighbours: Set<Coordinate>, area: Set<Coordinate> = emptySet()): Set<Coordinate> =
        if (neighbours.isEmpty()) area else flood(
            type = type,
            neighbours = neighbours
                .flatMap { it.orthogonalNeighbours() }
                .filter { it !in area && it !in neighbours && getOrNull(it) == type }
                .toSet(),
            area = area + neighbours,
        )

    private fun fenceAround(coordinates: Set<Coordinate>): Int =
        coordinates.sumOf {
            it.orthogonalNeighbours().count { it !in coordinates }
        }

    private fun countSides(coordinates: Set<Coordinate>): Int {
        val lines = mutableMapOf<Line, Set<IntRange>>()

        for (c in coordinates) {
            for (d in Direction.entries) {
                if (c.transpose(d.dx, d.dy) !in coordinates) {
                    val reference = d.dx.absoluteValue * c.x + d.dy.absoluteValue * c.y
                    val line = Line(reference, d)
                    val position = (1 - d.dx.absoluteValue) * c.x + (1 - d.dy.absoluteValue) * c.y
                    val ranges = lines[line] ?: emptySet()
                    val connections = ranges.filter { (position - 1) in it || (position + 1) in it }
                    lines[line] = (ranges - connections) + connect(connections, position)
                }
            }
        }
        return lines.entries.sumOf { (_, ranges) -> ranges.size }
    }

    private fun connect(connections: List<IntRange>, position: Int): Set<IntRange> {
        val min = min(connections.minOfOrNull { it.start } ?: 150, position)
        val max = max(connections.maxOfOrNull { it.endInclusive } ?: -1, position)
        return setOf(min..max)
    }
}
