package com.bartenkelaar.year2024.operation

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver

enum class Direction(val dx: Int, val dy: Int) {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    ;

    fun turn() = entries[(ordinal + 1) % 4]
}

class LabGuarder : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val lab = Grid.forChars(input)
        val guardPosition = lab.coordinateOfFirst('^')

        val route = lab.walkRoute(listOf(Direction.UP to guardPosition)).first
        val visited = route.map { it.second }.toSet()
        val loopOptions = (visited - guardPosition)
            .count { lab.with(it to '#').walkRoute(listOf(Direction.UP to guardPosition)).second }
        return visited.size to loopOptions
    }

    private tailrec fun Grid<Char>.walkRoute(route: List<Pair<Direction, Coordinate>>): Pair<List<Pair<Direction, Coordinate>>, Boolean> {
        val (d, position) = route.last()
        val next = position.step(d)
        if (next !in this) return route to false
        if ((d to next) in route) return route to true
        return if (get(next) == '#') walkRoute(route + (d.turn() to position)) else walkRoute(route + (d to next))
    }

    private fun Coordinate.step(d: Direction) = transpose(d.dx, d.dy)
}
