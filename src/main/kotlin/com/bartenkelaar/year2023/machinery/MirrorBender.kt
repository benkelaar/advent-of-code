package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.of
import kotlin.collections.mutableSetOf

class MirrorBender : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val rows = input.nonBlank()

        val startGrid = Grid.forChars(rows)
        val grids = startGrid.findCycle()
        val cycleStart = grids.indexOf(grids.last())
        val cycleSize = grids.size - cycleStart - 1
        val cyclesRemaining = (1_000_000_000 - cycleStart) % cycleSize

        return startGrid.roll().northBeamLoad() to grids[cycleStart + cyclesRemaining].northBeamLoad()
    }

    private fun Grid<Char>.findCycle(): List<Grid<Char>> {
        val foundGrids = mutableListOf<Grid<Char>>()
        var grid = this
        while (grid !in foundGrids) {
            foundGrids += grid
            grid = grid.cycle()
        }
        foundGrids += grid
        return foundGrids.toList()
    }

    private fun Grid<Char>.cycle(): Grid<Char> {
        val west = roll().roll { s, r -> Coordinate(r, s) }
        val south = west.roll { s, r -> Coordinate(s, rows.lastIndex - r) }
        return south.roll { s, r -> Coordinate(rows.lastIndex - r, s) }
    }

    private fun Grid<Char>.roll(location: (Int, Int) -> Coordinate = ::Coordinate): Grid<Char> {
        val stacks = (rowSize() of 0).toMutableList()
        val rollingStoneLocations = mutableSetOf<Coordinate>()
        for (rollIndex in rows.indices) {
            for (stackIndex in stacks.indices) {
                val rock = get(location(stackIndex, rollIndex))
                when (rock) {
                    'O' -> {
                        rollingStoneLocations += location(stackIndex, stacks[stackIndex])
                        stacks[stackIndex]++
                    }
                    '#' -> stacks[stackIndex] = rollIndex + 1
                }
            }
        }
        return mapCoordinated { c, s ->
            when {
                s == '#' -> '#'
                c in rollingStoneLocations -> 'O'
                else -> '.'
            }
        }
    }

    private fun Grid<Char>.northBeamLoad() = sumOfCoordinated { (_, y), c -> if (c == 'O') rowSize() - y else 0 }
}
