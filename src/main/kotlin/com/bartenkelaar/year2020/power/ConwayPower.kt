package com.bartenkelaar.year2020.power

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank

class ConwayPower : Solver {
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

        return ConwayPowerSource(cubeRange(xRange, yRange, zRange).filter { cubeCoordinate ->
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

data class CubeCoordinate(val x: Int, val y: Int, val z: Int) {
    fun findNeighbours(): Set<CubeCoordinate> {
        return cubeRange(x - 1..x + 1, y - 1..y + 1, z - 1..z + 1) - CubeCoordinate(x, y, z)
    }

    companion object {
        fun forChar(x: Int, y: Int, c: Char) = if (c == '#') CubeCoordinate(x, y, 0) else null
    }
}


private fun cubeRange(xRange: IntRange, yRange: IntRange, zRange: IntRange) =
    xRange.flatMap { xn -> yRange.flatMap { yn -> zRange.map { zn -> CubeCoordinate(xn, yn, zn) } } }.toSet()
