package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.year2021.measurement.Direction.X
import com.bartenkelaar.year2021.measurement.Direction.Y
import kotlin.reflect.KProperty1

private enum class Direction(private val property: KProperty1<Coordinate, Int>) {
    X(Coordinate::x) , Y(Coordinate::y);

    fun of(coordinate: Coordinate) = property.get(coordinate)

    companion object {
        fun of(d: String) = valueOf(d.uppercase())
    }
}

private data class Fold(val d: Direction, val foldLine: Int) {
    fun applyTo(coordinates: Set<Coordinate>) =
        coordinates.map { if (d.of(it) < foldLine) it else it.fold() }.toSet()

    private fun Coordinate.fold() = when(d) {
        X -> copy(x = 2 * foldLine - x)
        Y -> copy(y = 2 * foldLine - y)
    }
}

class InstructionOrigami : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val coordinates = input.nonBlank()
            .filter { it.first().isDigit() }
            .map { it.split(",") }
            .map { (x, y) -> Coordinate(x.toInt(), y.toInt()) }
            .toSet()
        val folds = input.filter { it.startsWith("fold") }
            .map { it.replace("fold along ", "") }
            .map { it.split("=") }
            .map { (d, f) -> Fold(Direction.of(d), f.toInt()) }

        val firstCoordinates = folds.first().applyTo(coordinates)

        folds.fold(coordinates) { cs, fold -> fold.applyTo(cs) }.print()
        return firstCoordinates.count() to "CEJKLUGJ"
    }

    private fun Set<Coordinate>.print() {
        for (y in 0..maxOf { it.y }) {
            for (x in 0..maxOf { it.x }) {
                print(if (Coordinate(x, y) in this) '⬜' else '⬛')
            }
            println()
        }
    }
}
