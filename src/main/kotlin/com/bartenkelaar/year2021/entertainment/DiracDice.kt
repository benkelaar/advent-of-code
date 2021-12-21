package com.bartenkelaar.year2021.entertainment

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.rot
import java.lang.Integer.min
import kotlin.math.max

private data class RoundState(val p1: Int, val p2: Int, val score1: Int, val score2: Int) {
    fun hasFinished() = player1Won() || score2 >= 21

    fun advance(roll: Int, count: Int, player1Turn: Boolean, universes: Long): Pair<RoundState, Long> {
        val newPosition1 = p1.takeIf { !player1Turn } ?: (p1 + roll).rot(10)
        val newPosition2 = p2.takeIf { player1Turn } ?: (p2 + roll).rot(10)
        return RoundState(
            p1 = newPosition1,
            p2 = newPosition2,
            score1 = score1.takeIf { !player1Turn } ?: (score1 + newPosition1),
            score2 = score2.takeIf { player1Turn } ?: (score2 + newPosition2),
        ) to universes * count
    }

    fun player1Won() = score1 >= 21
}

class DiracDice : Solver() {
    private val scoreUniverses = mapOf(
        3 to 1,
        4 to 3,
        5 to 6,
        6 to 7,
        7 to 6,
        8 to 3,
        9 to 1,
    )

    override fun solve(input: List<String>): Pair<Any, Any> {
        val initialPosition1 = input.first().lastDigit()
        val initialPosition2 = input[1].lastDigit()
        return deterministicDice(initialPosition1, initialPosition2) to diracDice(initialPosition1, initialPosition2)
    }

    private fun deterministicDice(initialPosition1: Int, initialPosition2: Int): Int {
        var player1Position1 = initialPosition1
        var player2Position1 = initialPosition2
        var dice = 0
        var score1 = 0
        var score2 = 0
        var player1Turn = true
        while (score1 < 1000 && score2 < 1000) {
            val roll = roll(dice)
            dice += 3
            if (player1Turn) {
                player1Position1 = (player1Position1 + roll).rot(10)
                score1 += player1Position1
            } else {
                player2Position1 = (player2Position1 + roll).rot(10)
                score2 += player2Position1
            }
            player1Turn = !player1Turn
        }
        return min(score1, score2) * dice
    }

    private fun diracDice(initialPosition1: Int, initialPosition2: Int): Long {
        val initialState = RoundState(initialPosition1, initialPosition2, 0, 0)
        val completed = mutableMapOf<RoundState, Long>()
        var ongoing = mutableMapOf(initialState to 1L)
        var player1Turn = true
        while (ongoing.isNotEmpty()) {
            val newStates = ongoing.flatMap { (game, universes) -> scoreUniverses.map { (roll, count) -> game.advance(roll, count, player1Turn, universes) } }
            ongoing = mutableMapOf()
            newStates.forEach { (game, universes) ->
                if (game.hasFinished()) completed.merge(game, universes) { a, b -> a + b}
                else ongoing.merge(game, universes) { a, b -> a + b }
            }
            player1Turn = !player1Turn
        }
        val universes = completed.values.sum()
        val player1WinCount = completed.entries.sumOf { (game, universes) -> if (game.player1Won()) universes else 0L }
        return max(player1WinCount, universes - player1WinCount)
    }
    private fun roll(dice: Int): Int = (dice + 1).rot(100) + (dice + 2).rot(100) + (dice + 3).rot(100)

    private fun String.lastDigit() = last().digitToInt()
}
