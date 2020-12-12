package com.bartenkelaar.year2020.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class DirectionTest {
    @Test
    fun rotate() {
        assertEquals(Direction.WEST, Direction.EAST.rotate('L', 2))
    }

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

    @Test
    fun duplicateRotate() {
        rotate('L', 1)
        assertEquals(-1, waypointX)
        assertEquals(10, waypointY)

        rotate('R',3)
        assertEquals(-10, waypointX)
        assertEquals(-1, waypointY)

        rotate('L', 3)
        assertEquals(-1, waypointX)
        assertEquals(10, waypointY)
    }
}

