package com.bartenkelaar.year2021

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver

private fun Coordinate.orWrapTop(height: Int) = copy(y = y.takeIf { it < height } ?: 0)
private fun Coordinate.orWrapLeft(width: Int) = copy(x = x.takeIf { it < width } ?: 0)

data class SeaCucumber(val south: Boolean, val gridHeight: Int, val gridWidth: Int) {
    fun targetFrom(c: Coordinate) =
        if (south) c.transpose(0, 1).orWrapTop(gridHeight) else c.transpose(1, 0).orWrapLeft(gridWidth)
    companion object {
        fun forChar(c: Char, width: Int, height: Int) = SeaCucumber(c == 'v', height, width).takeIf { c != '.' }
    }
}

class SeaCucumberMoverYear21Day25 : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val grid = Grid.forChars(input)
        val width = grid.rowSize()
        val height = grid.rows.size
        var floor = grid.map { SeaCucumber.forChar(it, width, height) }

        var count = 0
        while (floor.hasMovingCucumbers()) {
            count++
            floor = floor.moveEast(width)
            floor = floor.moveSouth(height)
        }

        return (count + 1) to 0
    }

    private fun Grid<SeaCucumber?>.hasMovingCucumbers() =
        anyCoordinated { c, sc -> sc != null && this[sc.targetFrom(c)] == null }

    private fun Coordinate.orWrapBottom(height: Int) = copy(y = y.takeIf { it >= 0 } ?: (height - 1))
    private fun Coordinate.orWrapRight(width: Int) = copy(x = x.takeIf { it >= 0 } ?: (width - 1))

    private fun Grid<SeaCucumber?>.moveEast(width: Int) =
        mapCoordinated { c, sc ->
            if (sc == null) this[c.transpose(-1, 0).orWrapRight(width)]?.takeUnless { it.south }
            else sc.takeIf { it.south || this[c.transpose(1, 0).orWrapLeft(width)] != null }
        }

    private fun Grid<SeaCucumber?>.moveSouth(height: Int) =
        mapCoordinated { c, sc ->
            if (sc == null) this[c.transpose(0, -1).orWrapBottom(height)]?.takeIf { it.south }
            else sc.takeIf { !it.south || this[c.transpose(0, 1).orWrapTop(height)] != null }
        }
}
