package com.bartenkelaar.year2015.elevators

import kotlin.test.Test
import kotlin.test.assertEquals

class BracketMoverTest {
    private val mover = BracketMover()

    @Test
    fun `given equal brackets expect zero movement`() {
        assertEquals(0, mover.moves("()()"))
        assertEquals(0, mover.moves("(())"))
    }

    @Test
    fun `given more opening brackets expect movement up`() {
        assertEquals(3, mover.moves("((("))
        assertEquals(3, mover.moves("(()(()("))
        assertEquals(3, mover.moves("))((((("))
    }

    @Test
    fun `given basement accessing `() {
        assertEquals(3, mover.moves("((("))
        assertEquals(3, mover.moves("(()(()("))
        assertEquals(3, mover.moves("))((((("))
    }
}