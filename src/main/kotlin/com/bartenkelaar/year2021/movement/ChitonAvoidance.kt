package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import java.util.*

data class Step(val location: Coordinate, val risk: Int)

data class ChitonPath(val path: List<Step>, val target: Coordinate) : Comparable<ChitonPath> {
    val location get() = path.last().location
    val totalRisk = path.sumOf { it.risk }
    private val cost = location.distanceTo(target) + totalRisk

    fun hasArrived() = location == target

    override fun compareTo(other: ChitonPath) = cost - other.cost
}

class ChitonAvoidance : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val riskMap = input.nonBlank().map { row -> row.split("").nonBlank().map { it.toInt() } }
        val path = findLowRiskPath(riskMap)

        val biggerRiskMap = (0 until 5).flatMap { j ->
            riskMap.map { row -> (0 until 5).flatMap { i -> row.map { wrapped(it + i + j) } } }
        }
        val biggerPath = findLowRiskPath(biggerRiskMap)

        return path.totalRisk to biggerPath.totalRisk
    }

    private tailrec fun wrapped(i: Int): Int = if (i > 9) wrapped(i - 9) else i

    private fun findLowRiskPath(riskMap: List<List<Int>>): ChitonPath {
        val columnLength = riskMap.size
        val rowLength = riskMap.first().size
        val start = Coordinate.ORIGIN
        val target = Coordinate(rowLength - 1, columnLength - 1)

        return riskMap.findPath(start, target, columnLength, rowLength)
    }

    private fun List<List<Int>>.findPath(from: Coordinate, to: Coordinate, rowLength: Int, columnLength: Int): ChitonPath {
        val initialPath = ChitonPath(listOf(Step(from, 0)), to)
        val locationCostMap = mutableMapOf(initialPath.location to 0)
        val queue = PriorityQueue(setOf(initialPath))

        tailrec fun recursiveFindPath(): ChitonPath {
            val closestPath = queue.remove()
            return if (closestPath.hasArrived()) closestPath else {
                val neighbours = closestPath.location.getNeighbours(rowLength, columnLength).map {
                    ChitonPath(closestPath.path + Step(it, this[it.y][it.x]), closestPath.target)
                }.filter { it.totalRisk < (locationCostMap[it.location] ?: Int.MAX_VALUE) }
                queue.addAll(neighbours)
                neighbours.forEach { locationCostMap[it.location] = it.totalRisk }
                recursiveFindPath()
            }
        }
        return recursiveFindPath()
    }

    private fun Coordinate.getNeighbours(rowLength: Int, columnLength: Int) =
        getNeighbours(x, y, rowLength, columnLength)

    private fun getNeighbours(x: Int, y: Int, rowLength: Int, columnLength: Int) =
        listOf(-1 to 0, 1 to 0, 0 to 1, 0 to -1)
            .map { (dx, dy) -> Coordinate(x, y).transpose(dx, dy) }
            .filter { it.isInRange(0, rowLength - 1, y2 = columnLength - 1) }
}
