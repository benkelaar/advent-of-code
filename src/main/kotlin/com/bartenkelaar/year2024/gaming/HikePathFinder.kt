package com.bartenkelaar.year2024.gaming

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver

class HikePathFinder : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val heightMap = Grid.forChars(input).map { it.digitToInt() }
        val starts = heightMap.toCoordinateMap().filter { (_, h) -> h == 0 }.keys

        val allPaths = heightMap.findPaths(starts.map { listOf(it) })
        val uniquePaths = allPaths.map { it.first() to it.last() }.toSet()
        return uniquePaths.size to allPaths.size
    }

    private fun Grid<Int>.findPaths(coordinates: List<List<Coordinate>>): List<List<Coordinate>> {
        val (finished, unfinished) = coordinates.partition { get(it.last()) == 9 }

        if (unfinished.isEmpty()) return finished

        return findPaths(
            finished + unfinished.flatMap { path ->
                val currentPosition = path.last()
                val currentHeight = get(currentPosition)
                val neighbours = currentPosition.orthogonalNeighbours().filter { it in this && get(it) == currentHeight + 1 }
                neighbours.map { nextStep -> path + nextStep }
            },
        )
    }
}
