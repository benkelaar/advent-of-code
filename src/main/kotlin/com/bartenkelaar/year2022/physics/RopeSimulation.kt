package com.bartenkelaar.year2022.physics

import com.bartenkelaar.util.*
import com.bartenkelaar.util.Coordinate.Companion.ORIGIN
import java.lang.StrictMath.abs
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.sign

private enum class RopeDirection(val char: Char, val step: Coordinate.() -> Coordinate) {
    UP('U', Coordinate::up),
    DOWN('D', Coordinate::down),
    LEFT('L', Coordinate::left),
    RIGHT('R', Coordinate::right);

    fun step(coordinate: Coordinate) = coordinate.step()

    companion object {
        fun forChar(d: String) = values().firstOrNull { it.char == d[0] }!!
    }
}

private data class Move(val d: RopeDirection, val s: Int)

private data class Position(
    val knots: List<Coordinate>,
    val tailHistory: Set<Coordinate> = setOf(knots.last())
) {
    fun move(move: Move): Position {
        val (direction, distance) = move

        var newKnots = knots
        val tailPositions = mutableSetOf<Coordinate>()
        repeat(distance) {
            var previousNew = direction.step(newKnots.first())
            newKnots = listOf(previousNew) + newKnots.tail().map { knot ->
                previousNew = knot.stepTo(previousNew)
                previousNew
            }
            tailPositions += newKnots.last()
        }

        return Position(newKnots, tailHistory + tailPositions)
    }
    private fun Coordinate.stepTo(other: Coordinate): Coordinate {
        val dx = other.x - x
        val dy = other.y - y
        return if (dx.absoluteValue <= 1 && dy.absoluteValue <= 1) this else transpose(
            dx.limitToSizeOne(),
            dy.limitToSizeOne()
        )
    }

    private fun Int.limitToSizeOne() = sign * min(absoluteValue, 1)
}

class RopeSimulation : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val moves = input.nonBlank()
            .map { it.split(" ") }
            .map { (direction, distance) -> Move(RopeDirection.forChar(direction), distance.toInt()) }

        val smallRopePosition = Position(2 of ORIGIN)
        val smallRopeTailHistory = moves.fold(smallRopePosition, Position::move).tailHistory

        val largeRopePosition = Position(10 of ORIGIN)
        val largeRopeTailHistory = moves.fold(largeRopePosition, Position::move).tailHistory

        return smallRopeTailHistory.size to largeRopeTailHistory.size;
    }
}