package com.bartenkelaar.year2015.movement

import com.bartenkelaar.util.Solver

class BracketMover : Solver() {
    override fun solve(input: List<String>) = with(input.first()) { moves(this) to 0 }

    fun moves(brackets: String) = brackets.count { it == '(' } - brackets.count { it == ')' }
}
