package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.of

class HydroMeasurer : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val lines = input.nonBlank()
            .map { it.replace(" -> ", ",") }
            .map { it.split(",") }
            .map { it.map { it.toInt() } }
        val horizontalLines = lines.filter { (x1, y1, x2, y2) -> x1 == x2 || y1 == y2 }
        val maxX = lines.flatMap { listOf(it[0], it[2]) }.maxOrNull()!! + 1
        val maxY = lines.flatMap { listOf(it[1], it[3]) }.maxOrNull()!! + 1

        val horizontalVents = (maxY of { (maxX of 0).toMutableList() }).toMutableList()
        horizontalLines.forEach { (x1, y1, x2, y2) ->
            val (minY, maxY) = listOf(y1, y2).sorted()
            val (minX, maxX) = listOf(x1, x2).sorted()
            for (y in minY..maxY) {
                for (x in minX..maxX) {
                    horizontalVents[y][x] += 1
                }
            }
        }

        val vents = (maxY of { (maxX of 0).toMutableList() }).toMutableList()
        lines.forEach { (x1, y1, x2, y2) ->
            val (minY, maxY) = listOf(y1, y2).sorted()
            val (minX, maxX) = listOf(x1, x2).sorted()

            if (minY != maxY) {
                if (minX == maxX) {
                    for (y in minY..maxY) {
                        vents[y][minX] += 1
                    }
                } else {
                    val xDir = if (x1 > x2) -1 else 1
                    val yDir = if (y1 > y2) -1 else 1
                    for (i in 0..(maxY-minY)) {
                        val x = x1 + i*xDir
                        val y = y1 + i*yDir
                        vents[y][x] += 1
                    }
                }
            } else {
                for (x in minX..maxX) {
                    vents[minY][x] += 1
                }
            }
        }
        return horizontalVents.countBiggerThanOne() to vents.countBiggerThanOne()
    }

    private fun List<List<Int>>.countBiggerThanOne() = sumOf { row -> row.count { it > 1 } }
}