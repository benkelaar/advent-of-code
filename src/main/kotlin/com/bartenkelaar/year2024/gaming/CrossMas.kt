package com.bartenkelaar.year2024.gaming

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver

class CrossMas : Solver() {
    private val directions =
        (-1..1)
            .flatMap { dx -> (-1..1).map { dx to it } }
            .filter { it != 0 to 0 }

    override fun solve(input: List<String>): Pair<Any, Any> {
        val charGrid = Grid.forChars(input)
        val xMasCount =
            charGrid.sumOfCoordinated { co, ch -> charGrid.countXmasAt(co, ch) }
        val crossMasCount =
            charGrid.sumOfCoordinated { co, ch -> if (charGrid.isCrossMas(co, ch)) 1 else 0 }
        return xMasCount to crossMasCount
    }

    private fun Grid<Char>.countXmasAt(
        c: Coordinate,
        char: Char,
    ): Int {
        if (char != 'X') return 0
        return directions.count { (dx, dy) ->
            getOrNull(c.transpose(dx, dy)) == 'M' &&
                getOrNull(c.transpose(2 * dx, 2 * dy)) == 'A' &&
                getOrNull(c.transpose(3 * dx, 3 * dy)) == 'S'
        }
    }

    private fun Grid<Char>.isCrossMas(
        c: Coordinate,
        char: Char,
    ): Boolean {
        if (char != 'A') return false
        val lastIndex = rowSize() - 1
        if (c.x == 0 || c.y == 0 || c.x == lastIndex || c.y == lastIndex) return false

        val cornerDirections = directions.filter { (dx, dy) -> dx != 0 && dy != 0 }
        val corners = cornerDirections.associateWith { (dx, dy) -> get(c.transpose(dx, dy)) }

        return corners.values.count { it == 'S' } == 2 &&
            corners.values.count { it == 'M' } == 2 &&
            (corners[-1 to -1] == corners[-1 to 1] || corners[-1 to -1] == corners[1 to -1])
    }
}
