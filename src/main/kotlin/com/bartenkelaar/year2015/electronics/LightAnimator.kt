package com.bartenkelaar.year2015.electronics

import com.bartenkelaar.util.*
import com.bartenkelaar.util.Bit.Companion.SET

class LightAnimator : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val initialLights = Grid.forBits(input, '#')
        val simpleLights = (1..100).fold(initialLights) { lights, _ -> lights.mapWithNeighbours(::conwayRule) }
        val stuckLights = (1..100).fold(initialLights.withLitCorners()) { lights, _ ->
            lights.mapWithNeighbours(::conwayRule).withLitCorners()
        }

        return simpleLights.count() to stuckLights.count()
    }

    private fun conwayRule(bit: Bit, neighbourGrid: Grid<Bit>) =
        Bit(neighbourGrid.count() == 3 || (bit() && neighbourGrid.count() == 4))

    private fun Grid<Bit>.withLitCorners() = with(
        Coordinate(0, 0) to SET,
        Coordinate(99, 0) to SET,
        Coordinate(0, 99) to SET,
        Coordinate(99, 99) to SET,
    )
}
