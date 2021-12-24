package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Coordinate3D
import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.tail
import com.bartenkelaar.util.zipPerEmpty

private val xRotations = 0..3
private val yRotations = xRotations.flatMap { x -> (0..3).map { y -> Coordinate3D(x, y, 0) } }
private val allRotations = yRotations + xRotations.flatMap { x -> listOf(1, 3).map { z -> Coordinate3D(x, 0, z) } }

private data class ScannerMeasurements(val measurements: Set<Coordinate3D>) {
    val rotations = allRotations.associateWith { rotation -> measurements.map { it.rotate(rotation) }.toSet() }

    fun matches(other: ScannerMeasurements): MatchResult? {
        fun recursiveMatches(targetSet: Set<Coordinate3D>): Coordinate3D? =
            if (targetSet.size < 12) null else
                measurements.firstNotNullOfOrNull { firstOverlaps(it, targetSet) } ?:
                recursiveMatches(targetSet - targetSet.first())

        val result = other.rotations.firstNotNullOfOrNull { (rotation, otherMeasurements) ->
            recursiveMatches(otherMeasurements)?.let { rotation to it }
        }
        return result?.let { (rotation, offset) -> MatchResult(other.adjust(rotation, offset), other, offset) }
    }

    fun adjust(rotation: Coordinate3D, offset: Coordinate3D) =
        ScannerMeasurements(measurements.map { it.rotate(rotation) + offset }.toSet())

    operator fun plus(other: ScannerMeasurements) = ScannerMeasurements(measurements + other.measurements)

    private fun firstOverlaps(reference: Coordinate3D, targetSet: Set<Coordinate3D>): Coordinate3D? {
        val targetReference = targetSet.first()
        val offset = reference - targetReference
        val others = targetSet - targetReference
        val ours = measurements - reference
        return offset.takeIf { ours.intersect(others.map { it + offset }.toSet()).size >= 11 }
    }
}

private data class MatchResult(val original: ScannerMeasurements, val match: ScannerMeasurements, val offset: Coordinate3D)

class BeaconScanner : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val scannerMeasurements = input.zipPerEmpty().map { measurements ->
            ScannerMeasurements(measurements.tail()
                .map { row -> row.split(",").map { it.toInt() } }
                .map { (x, y, z) -> Coordinate3D(x, y, z) }
                .toSet()
            )
        }

        var measurements = scannerMeasurements.first()
        val remainder = (scannerMeasurements - measurements).toMutableList()
        val scanners = mutableListOf(Coordinate3D(0, 0, 0))

        while (remainder.isNotEmpty()) {
            val (transform, match, offset) = remainder.firstNotNullOf { measurements.matches(it) }
            measurements += transform
            remainder -= match
            scanners += offset
            println("Found match, options remaining: ${remainder.size}")
        }

        return measurements.measurements.size to scanners.maxOf { from -> scanners.maxOf { to -> from.manhattan(to) } }
    }
}
