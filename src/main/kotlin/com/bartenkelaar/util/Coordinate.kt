package com.bartenkelaar.util

data class Coordinate(val x: Int, val y: Int) {
    fun transpose(dx: Int, dy: Int) = Coordinate(x + dx, y + dy)

    /**
     * Checks for whether x and y are respectively with in [x1, x2] and [y1,y2] (both inclusive)
     */
    fun isInRange(x1: Int, x2: Int, y1: Int = x1, y2: Int = x2) = x in x1..x2 && y in y1..y2

    companion object {
        val ORIGIN = Coordinate(0, 0)
    }
}

operator fun <T> List<List<T>>.get(coordinate: Coordinate) = this[coordinate.y][coordinate.x]