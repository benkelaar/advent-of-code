package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.tail
import com.bartenkelaar.util.zipPerEmpty
import kotlin.math.absoluteValue

private val xRotations = 0..3
private val yRotations = xRotations.flatMap { x -> (0..3).map { y -> Coordinate3D(x, y, 0) } }
private val allRotations = yRotations + xRotations.flatMap { x -> listOf(1, 3).map { z -> Coordinate3D(x, 0, z) } }

private data class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    operator fun minus(other: Coordinate3D) = Coordinate3D(x - other.x, y - other.y, z - other.z)
    operator fun plus(offset: Coordinate3D) = Coordinate3D(x + offset.x, y + offset.y, z + offset.z)

    fun rotate(angles: Coordinate3D): Coordinate3D {
        val (y1, z1) = rotate(angles.x, y, z)
        val (x1, z2) = rotate(angles.y, x, z1)
        val (x2, y2) = rotate(angles.z, x1, y1)
        return Coordinate3D(x2, y2, z2)
    }

    private fun rotate(amount: Int, x: Int, y: Int) = listOf(x, y, -x, -y, x).subList(amount, amount + 2)
    fun manhattan(target: Coordinate3D) = (x - target.x).absoluteValue + (y - target.y).absoluteValue + (z - target.z).absoluteValue
}

private data class MatchResult(val original: ScannerMeasurements, val match: ScannerMeasurements, val offset: Coordinate3D)

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

    private fun firstOverlaps(reference: Coordinate3D, targetSet: Set<Coordinate3D>): Coordinate3D? {
        val targetReference = targetSet.first()
        val offset = reference - targetReference
        val others = targetSet - targetReference
        val ours = measurements - reference
        return offset.takeIf { ours.intersect(others.map { it + offset }.toSet()).size >= 11 }
    }

    operator fun plus(other: ScannerMeasurements) = ScannerMeasurements(measurements + other.measurements)
}

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
