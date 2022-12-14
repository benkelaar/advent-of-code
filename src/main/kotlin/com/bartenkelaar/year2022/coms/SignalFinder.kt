package com.bartenkelaar.year2022.coms

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver
import java.util.PriorityQueue

class SignalFinder : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val charGrid = Grid.forChars(input)
        val start = charGrid.coordinateOfFirst('S')

        val target = charGrid.coordinateOfFirst('E')
        val heightMap = charGrid.map { it.toHeight() }
        fun List<Coordinate>.score() = heightMap.distance(last(), target) + size

        val scoreIt: List<Coordinate>.() -> Int = List<Coordinate>::score;
        val startValues = listOf(start)

        val stepsFromStart = heightMap.findShortestPath(startValues, target, scoreIt)
        val lowest = heightMap.mapCoordinated { c, i -> c.takeIf { i == 1 } }.rows.flatten().filterNotNull()
        val stepsFromLowest = heightMap.findShortestPath(lowest, target, scoreIt)

        return stepsFromStart to stepsFromLowest
    }

    private fun Grid<Int>.findShortestPath(
        startValues: List<Coordinate>,
        target: Coordinate,
        scoreIt: List<Coordinate>.() -> Int
    ): Int {
        val cache = startValues.associateWith { 0 }.toMutableMap()
        val queue = PriorityQueue<List<Coordinate>> { p1, p2 -> p1.scoreIt().compareTo(p2.scoreIt()) }
        startValues.forEach { queue.offer(listOf(it))}

        var steps = 0
        do {
            val path = queue.poll()
            options(path.last(), path, cache).forEach {
                cache[it] = path.size + 1
                queue.offer(path + it)
            }
            steps = path.size - 1
        } while (path.last() != target)
        return steps
    }

    private fun Grid<Int>.distance(from: Coordinate, to: Coordinate) =
        from.distanceTo(to) + (this[to] - this[from])

    private fun Grid<Int>.options(coordinate: Coordinate, path: List<Coordinate>, cache: Map<Coordinate, Int>) =
        coordinate.orthogonalNeighbours().filter {
            it !in path &&
            (cache[it] ?: Int.MAX_VALUE) > path.size + 1 &&
            it in this &&
            this[it] <= this[coordinate] + 1
        }

    private fun Char.toHeight() = when(this) {
        'S'-> 1
        'E'-> 26
        else-> code - 96
    }
}
