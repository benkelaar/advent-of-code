package com.bartenkelaar.year2020.seats

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.sign

private typealias Neighbours = (Int, Int, List<List<String>>) -> List<String>

class ConwaySeating : Solver() {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val seats = input.nonBlank().map { it.split("") }
        return runTillStable(seats, this::findNeighbours, 4).countSeats() to
                runTillStable(seats, this::findBetterNeighbours, 5).countSeats()
    }

    private fun runTillStable(seats: List<List<String>>, neighbours: Neighbours, boundary: Int): List<List<String>> {
        var previousSeats = seats
        var nextSeats = nextRound(seats, neighbours, boundary)
        while (nextSeats != previousSeats) {
            previousSeats = nextSeats
            nextSeats = nextRound(nextSeats, neighbours, boundary)
        }
        return nextSeats
    }

    private fun nextRound(
        seats: List<List<String>>,
        findNeighbours: Neighbours,
        boundary: Int
    ) = seats.mapIndexed { y, row ->
        row.mapIndexed { x, seat ->
            val neighbours = findNeighbours(x, y, seats)
            if (seat == "#" && neighbours.count { it == "#" } >= boundary) "L" else
                if (seat == "L" && neighbours.all { it != "#" }) "#" else seat
        }
    }

    private fun findNeighbours(x: Int, y: Int, seats: List<List<String>>): List<String> {
        val lastIndex = seats.first().lastIndex
        val xRange = max(x - 1, 0)..min(x + 1, lastIndex)
        val topNeighbours = if (y != 0) seats[y - 1].slice(xRange) else emptyList()
        val bottomNeighbours = if (y != seats.lastIndex) seats[y + 1].slice(xRange) else emptyList()
        val rowNeighbours = (if (x != 0) listOf(seats[y][x - 1]) else emptyList()) +
                if (x != lastIndex) listOf(seats[y][x + 1]) else emptyList()
        return topNeighbours + rowNeighbours + bottomNeighbours
    }

    private fun findBetterNeighbours(x: Int, y: Int, seats: List<List<String>>): List<String> {
        val directions = (-1..1).flatMap { x1 -> (-1..1).map { y1 -> x1 to y1 } } - (0 to 0)
        return directions.mapNotNull { seats.findFirstSeat(x, y, it.first, it.second) }
    }

    private fun List<List<String>>.findFirstSeat(xStart: Int, yStart: Int, deltaX: Int, deltaY: Int): String? {
        val x = xStart + deltaX
        val y = yStart + deltaY
        if (x < 0 || x >= first().size || y < 0 || y >= size) return null
        val seat = get(y)[x]
        return if (seat == ".")
            findFirstSeat(xStart, yStart, deltaX + deltaX.sign, deltaY + deltaY.sign)
        else seat
    }
}

private fun List<List<String>>.countSeats() = map { it.count { it == "#" } }.sum()
