package com.bartenkelaar.year2020.navigation

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank
import kotlin.math.absoluteValue

class FerrySailer : Solver {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val sails = input.nonBlank().map { Sail(it[0], it.slice(1..it.lastIndex).toInt()) }
        return directControlManhattan(sails) to waypointManhattan(sails)
    }

    private fun directControlManhattan(sails: List<Sail>): Int {
        var dir = Direction.EAST
        var x = 0
        var y = 0

        fun move(direction: Char, amount: Int) {
            when (direction) {
                'E' -> x += amount
                'S' -> y -= amount
                'W' -> x -= amount
                'N' -> y += amount
            }
        }

        sails.forEach {
            when (it.dir) {
                'E', 'S', 'N', 'W' -> move(it.dir, it.amount)
                'L', 'R' -> dir = dir.rotate(it.dir, it.amount / 90)
                'F' -> move(dir.c, it.amount)
            }
        }

        return x.absoluteValue + y.absoluteValue
    }

    private fun waypointManhattan(sails: List<Sail>): Int {
        var x = 0
        var y = 0
        var waypointX = 10
        var waypointY = 1

        fun rotate(direction: Char, amount: Int) {
            if (amount == 2) {
                waypointX = -waypointX
                waypointY = -waypointY
            } else {
                val sign = (if (direction == 'L') -1 else 1) * (2 - amount)
                val newX = sign * waypointY
                val newY = -sign * waypointX
                waypointX = newX
                waypointY = newY
            }
        }

        sails.forEach {
            when (it.dir) {
                'E' -> waypointX += it.amount
                'S' -> waypointY -= it.amount
                'W' -> waypointX -= it.amount
                'N' -> waypointY += it.amount
                'L', 'R' -> rotate(it.dir, it.amount / 90)
                'F' -> {
                    x += waypointX * it.amount
                    y += waypointY * it.amount
                }
            }
        }

        return x.absoluteValue + y.absoluteValue
    }
}

data class Sail(val dir: Char, val amount: Int)

enum class Direction(val c: Char) {
    EAST('E'),
    SOUTH('S'),
    WEST('W'),
    NORTH('N');

    fun rotate(s: Char, slots: Int): Direction {
        val sign = if (s == 'L') -1 else 1
        val movement = sign * slots
        val target = ordinal + movement
        return values()[if (target < 0) 4 + target else if (target > 3) target - 4 else target]
    }
}