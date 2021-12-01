package com.bartenkelaar.year2020.power

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

class ConwayPower : Solver() {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val cubes = input.nonBlank().map { it.toCharArray().toList() }
        var source = ConwayPowerSource.forInput(cubes)
        for (i in 1..6) {
            source = source.cycle()
        }

        return source.activeCount() to 0
    }
}

data class ConwayPowerSource(val activeCubes: Set<CubeCoordinate>) {
    fun activeCount() = activeCubes.size

    fun cycle(): ConwayPowerSource {
        val xRange = range { x }
        val yRange = range { y }
        val zRange = range { z }
        val wRange = range { w }

        return ConwayPowerSource(cubeRange(xRange, yRange, zRange, wRange).filter { cubeCoordinate ->
            val activeNeighbours = cubeCoordinate.findNeighbours().filter { it in activeCubes }.count()
            activeNeighbours == 3 || cubeCoordinate in activeCubes && activeNeighbours == 2
        }.toSet())
    }

    private fun range(property: CubeCoordinate.() -> Int) = min(property) - 1..max(property) + 1
    private fun min(property: CubeCoordinate.() -> Int) = activeCubes.map(property).minOrNull()!!
    private fun max(property: CubeCoordinate.() -> Int) = activeCubes.map(property).maxOrNull()!!

    companion object {
        fun forInput(cubes: List<List<Char>>) = ConwayPowerSource(cubes.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, c -> CubeCoordinate.forChar(x, y, c) }
        }.toSet())
    }
}

data class CubeCoordinate(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun findNeighbours(): Set<CubeCoordinate> {
        return cubeRange(
            xRange = x - 1..x + 1,
            yRange = y - 1..y + 1,
            zRange = z - 1..z + 1,
            wRange = w - 1..w + 1
        ) - CubeCoordinate(x, y, z, w)
    }

    companion object {
        fun forChar(x: Int, y: Int, c: Char) = if (c == '#') CubeCoordinate(x, y, 0, 0) else null
    }
}

private fun cubeRange(xRange: IntRange, yRange: IntRange, zRange: IntRange, wRange: IntRange) =
    xRange.flatMap { xn ->
        yRange.flatMap { yn ->
            zRange.flatMap { zn ->
                wRange.map { wn -> CubeCoordinate(xn, yn, zn, wn) }
            }
        }
    }.toSet()
