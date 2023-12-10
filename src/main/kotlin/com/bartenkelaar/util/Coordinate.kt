package com.bartenkelaar.util

import kotlin.math.abs
import kotlin.math.absoluteValue

data class Coordinate(val x: Int, val y: Int) {
    fun transpose(dx: Int, dy: Int) = Coordinate(x + dx, y + dy)

    /**
     * Checks for whether x and y are respectively with in [x1, x2] and [y1,y2] (both inclusive)
     */
    fun isInRange(x1: Int, x2: Int, y1: Int = x1, y2: Int = x2) = x in x1..x2 && y in y1..y2

    fun distanceTo(target: Coordinate) = abs(x - target.x) + abs(y - target.y)

    fun left() = copy(x = x - 1)
    fun right() = copy(x = x + 1)
    fun up() = copy(y = y - 1)
    fun down() = copy(y = y + 1)

    fun orthogonalNeighbours() = listOf(left(), right(), up(), down())

    companion object {
        val ORIGIN = Coordinate(0, 0)

        fun from(xy: Pair<Int, Int>) = Coordinate(xy.first, xy.second)
    }
}

operator fun <T> List<List<T>>.get(coordinate: Coordinate) = this[coordinate.y][coordinate.x]
data class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    fun manhattan(target: Coordinate3D) = (x - target.x).absoluteValue + (y - target.y).absoluteValue + (z - target.z).absoluteValue

    operator fun minus(other: Coordinate3D) = Coordinate3D(x - other.x, y - other.y, z - other.z)
    operator fun plus(offset: Coordinate3D) = Coordinate3D(x + offset.x, y + offset.y, z + offset.z)
    fun rotate(angles: Coordinate3D): Coordinate3D {
        val (y1, z1) = rotate(angles.x, y, z)
        val (x1, z2) = rotate(angles.y, x, z1)
        val (x2, y2) = rotate(angles.z, x1, y1)
        return Coordinate3D(x2, y2, z2)
    }

    private fun rotate(amount: Int, x: Int, y: Int) = listOf(x, y, -x, -y, x).subList(amount, amount + 2)
}