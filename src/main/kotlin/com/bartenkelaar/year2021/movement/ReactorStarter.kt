package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.*
import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*

data class LitArea(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
    fun size() = xRange.length().toLong() * yRange.length() * zRange.length()

    fun overlaps(area: LitArea) =
        area.xRange.overlaps(xRange) && area.yRange.overlaps(yRange) && area.zRange.overlaps(zRange)

    operator fun minus(other: LitArea): Set<LitArea> {
        val result = mutableSetOf<LitArea>()
        if (other.zRange.startsIn(zRange)) result += copy(zRange = zRange.before(other.zRange))
        if (other.zRange.endsIn(zRange)) result += copy(zRange = zRange.after(other.zRange))
        val overlappedZRange = zRange.overlapWith(other.zRange)
        if (other.yRange.startsIn(yRange)) result += copy(yRange = yRange.before(other.yRange), zRange = overlappedZRange)
        if (other.yRange.endsIn(yRange)) result += copy(yRange = yRange.after(other.yRange), zRange = overlappedZRange)
        val overlappedYRange = yRange.overlapWith(other.yRange)
        if (other.xRange.startsIn(xRange)) result += LitArea(xRange.before(other.xRange), overlappedYRange, overlappedZRange)
        if (other.xRange.endsIn(xRange)) result += LitArea(xRange.after(other.xRange), overlappedYRange, overlappedZRange)

        return result
    }

    fun ifOn(step: RebootStep) = setOf(this).takeIf { step.on } ?: emptySet()

    private fun IntRange.overlapWith(other: IntRange) = max(first, other.first)..min(last, other.last)
    private fun IntRange.startsIn(other: IntRange) = first in (other.first+1)..other.last
    private fun IntRange.endsIn(other: IntRange) = last in other.first until other.last
    private fun IntRange.before(other: IntRange) = first until other.first
    private fun IntRange.after(other: IntRange) = (other.last + 1)..last
    private fun IntRange.overlaps(other: IntRange) =
        first in other || last in other || other.first in this || other.last in this
    private fun IntRange.length() = endInclusive - start + 1
}

data class RebootStep(val on: Boolean, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
    fun forCoordinates(action: (Coordinate3D) -> Unit) {
        zRange.forEach { z -> yRange.forEach { y -> xRange.forEach { x -> action(Coordinate3D(x, y, z)) } } }
    }

    fun toArea() = LitArea(xRange, yRange, zRange)

    companion object {
        // on x=-12..41,y=-1..48,z=-27..19
        private val regex = """(?:on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)""".toRegex()

        fun forLine(line: String): RebootStep {
            val (x1, x2, y1, y2, z1, z2) = regex.matchEntire(line)!!.destructured
            return RebootStep(
                on = line.startsWith("on"),
                xRange = x1.toInt()..x2.toInt(),
                yRange = y1.toInt()..y2.toInt(),
                zRange = z1.toInt()..z2.toInt()
            )
        }
    }
}

class ReactorStarter : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val steps = input.nonBlank().map { RebootStep.forLine(it) }
        val smallSteps = steps.subList(0, 20)

        val initialized = runSteps(smallSteps)
        val rebooted = runStepsSmarter(steps)
        return initialized to rebooted
    }

    private fun runStepsSmarter(steps: List<RebootStep>) =
        steps.fold(setOf<LitArea>()) { areas, step ->
            val area = step.toArea()
            val (overlapping, others) = areas.partition { it.overlaps(area) }
            overlapping.flatMap { it - area }.toSet() + others + area.ifOn(step)
        }.sumOf { it.size() }

    private fun runSteps(smallSteps: List<RebootStep>): Long {
        val zOffset = smallSteps.minOf { it.zRange.first }
        val zRange = smallSteps.maxOf { it.zRange.last } - zOffset
        val yOffset = smallSteps.minOf { it.yRange.first }
        val yRange = smallSteps.maxOf { it.yRange.last } - yOffset
        val xOffset = smallSteps.minOf { it.xRange.first }

        val grid = (zRange + 1) of { (yRange + 1) of { BitSet() } }
        for (step in smallSteps) {
            step.forCoordinates { (x, y, z) -> grid[z - zOffset][y - yOffset][x - xOffset] = step.on }
        }
        return grid.sumOf { square -> square.sumOf { it.cardinality().toLong() } }
    }
}
