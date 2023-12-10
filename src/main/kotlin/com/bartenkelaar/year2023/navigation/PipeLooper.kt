package com.bartenkelaar.year2023.navigation

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver

typealias NextPipe = (Coordinate) -> Coordinate

private enum class Direction {
    RIGHT, LEFT, UP, DOWN
}

private enum class Pipe(val sign: Char, val from: NextPipe, val to: NextPipe) {
    HORIZONTAL('-', Coordinate::left, Coordinate::right),
    VERTICAL('|', Coordinate::up, Coordinate::down),
    TOP_LEFT('J', Coordinate::up, Coordinate::left),
    TOP_RIGHT('L', Coordinate::up, Coordinate::right),
    BOTTOM_LEFT('7', Coordinate::down, Coordinate::left),
    BOTTOM_RIGHT('F', Coordinate::down, Coordinate::right);

    fun connections(coordinate: Coordinate) = listOf(from(coordinate), to(coordinate))

    companion object {
        fun fromChar(char: Char) = entries.firstOrNull { it.sign == char }
    }
}

class PipeLooper : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val start = Coordinate.from(input.mapIndexedNotNull { y, row ->
            row.indexOf("S").takeIf { it >= 0 }?.let { it to y }
        }.single())
        val grid = Grid.forChars(input).map { Pipe.fromChar(it) }
        val outgoingPipes = start.orthogonalNeighbours()
            .filter { grid.getOrNull(it)?.connections(it)?.contains(start) ?: false }
            .map { listOf(start, it) }
        val loop = findLoopHalves(grid, outgoingPipes).first()

        return loop.size / 2 to countEnclosed(grid, loop)
    }

    private fun countEnclosed(grid: Grid<Pipe?>, loop: List<Coordinate>): Int {
        val minX = loop.minOf { it.x }
        val minY = loop.filter { it.x == minX }.minOf { it.y }

        val extreme = Coordinate(minX, minY)
        val start = extreme.right()
        val clockWise = if (loop.indexOf(start) < loop.indexOf(extreme)) loop.reversed() else loop
        val startIndex = clockWise.indexOf(start)
        val reorientedLoop = clockWise.subList(startIndex, clockWise.size) + clockWise.subList(0, startIndex)

        var facing = Direction.RIGHT
        val outsideSpots = mutableSetOf<Coordinate>()
        val insideSpots = mutableSetOf<Coordinate>()

        fun MutableSet<Coordinate>.addSpaces(vararg space: Coordinate) =
            addAll(space.toSet().filter { it !in loop && grid.contains(it) })

        for (location in reorientedLoop) {
            var pipe = grid[location]

            if (pipe == null) { // happens at start
                if (reorientedLoop.indexOf(location) == reorientedLoop.lastIndex) continue
                val next = reorientedLoop[reorientedLoop.indexOf(location) + 1]
                val previous = reorientedLoop[reorientedLoop.indexOf(location) - 1]
                val borderingLocations = setOf(next, previous)
                pipe = when {
                    location.down() in borderingLocations && location.left() in borderingLocations -> Pipe.BOTTOM_LEFT
                    location.down() in borderingLocations && location.up() in borderingLocations -> Pipe.VERTICAL
                    location.down() in borderingLocations && location.right() in borderingLocations -> Pipe.BOTTOM_RIGHT
                    location.left() in borderingLocations && location.up() in borderingLocations -> Pipe.TOP_LEFT
                    location.left() in borderingLocations && location.right() in borderingLocations -> Pipe.HORIZONTAL
                    location.right() in borderingLocations && location.up() in borderingLocations -> Pipe.TOP_RIGHT
                    else -> throw IllegalStateException()
                }
            }

            when (facing) {
                Direction.RIGHT -> {
                    when(pipe) {
                        Pipe.HORIZONTAL -> {
                            outsideSpots.addSpaces(location.up())
                            insideSpots.addSpaces(location.down())
                        }
                        Pipe.TOP_LEFT -> {
                            facing = Direction.UP
                            insideSpots.addSpaces(location.right(), location.down())
                        }
                        Pipe.BOTTOM_LEFT -> {
                            facing = Direction.DOWN
                            outsideSpots.addSpaces(location.up(), location.right())
                        }
                        else -> throw IllegalStateException()
                    }
                }
                Direction.LEFT -> {
                    when(pipe) {
                        Pipe.HORIZONTAL -> {
                            outsideSpots.addSpaces(location.down())
                            insideSpots.addSpaces(location.up())
                        }
                        Pipe.TOP_RIGHT -> {
                            facing = Direction.UP
                            outsideSpots.addSpaces(location.left(), location.down())
                        }
                        Pipe.BOTTOM_RIGHT -> {
                            facing = Direction.DOWN
                            insideSpots.addSpaces(location.up(), location.left())
                        }
                        else -> throw IllegalStateException()
                    }
                }
                Direction.UP -> {
                    when (pipe) {
                        Pipe.VERTICAL -> {
                            outsideSpots.addSpaces(location.left())
                            insideSpots.addSpaces(location.right())
                        }
                        Pipe.BOTTOM_LEFT -> {
                            facing = Direction.LEFT
                            insideSpots.addSpaces(location.right(), location.up())
                        }
                        Pipe.BOTTOM_RIGHT -> {
                            facing = Direction.RIGHT
                            outsideSpots.addSpaces(location.left(), location.up())
                        }
                        else -> throw IllegalStateException()
                    }
                }
                Direction.DOWN -> {
                    when(pipe) {
                        Pipe.VERTICAL -> {
                            outsideSpots.addSpaces(location.right())
                            insideSpots.addSpaces(location.left())
                        }
                        Pipe.TOP_LEFT -> {
                            facing = Direction.LEFT
                            outsideSpots.addSpaces(location.right(), location.down())
                        }
                        Pipe.TOP_RIGHT -> {
                            facing = Direction.RIGHT
                            insideSpots.addSpaces(location.left(), location.down())
                        }
                        else -> throw IllegalStateException()
                    }
                }
            }

        }

        val filledAreas = insideSpots.floodFill(loop.toSet())

        return filledAreas.count()
    }

    private tailrec fun Set<Coordinate>.floodFill(loop: Set<Coordinate>): Set<Coordinate> {
        val neighbours = flatMap { it.orthogonalNeighbours().filter { n -> n !in loop && n !in this } }
        return if (neighbours.isEmpty()) this else (this + neighbours).floodFill(loop)
    }

    private tailrec fun findLoopHalves(grid: Grid<Pipe?>, pipes: List<List<Coordinate>>): List<List<Coordinate>> {
        val nextPipes = pipes.map { grid.nextPipe(it) }
        if (nextPipes.all { it == null }) return pipes
        return findLoopHalves(grid, pipes.zip(nextPipes).mapNotNull { (p, n) -> n?.let { p + it } })
    }

    private fun Grid<Pipe?>.nextPipe(pipe: List<Coordinate>) =
        getOrNull(pipe.last())?.connections(pipe.last())?.firstOrNull { it !in pipe }
}