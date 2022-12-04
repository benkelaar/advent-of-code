package com.bartenkelaar.year2022.gaming

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.year2022.gaming.Move.*
import javax.print.attribute.standard.MediaSize

enum class Move(val score: Int, val defeats: Move?) {
    ROCK(1, null), PAPER(2, ROCK), SCISSORS(3, PAPER);

    fun defeats(other: Move) =
        other == defeats || defeats == null && other == SCISSORS

    fun losesTo() = when(this) {
        ROCK -> PAPER
        PAPER -> SCISSORS
        SCISSORS -> ROCK
    }
}

class RockPaperScissorsGuide : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val moves = input.nonBlank()
            .map { it.split(" ") }
        return moves.sumOf { (them, us) -> score(them.theirMove(), us.ourMove()) } to
                moves.sumOf { (them, us) -> targetedScore(them.theirMove(), us) }
    }

    private fun score(theirMove: Move, ourMove: Move)
        = ourMove.score +
            if (theirMove == ourMove) 3 else
                if (ourMove.defeats(theirMove)) 6 else 0

    private fun targetedScore(theirMove: Move, target: String) = when (target) {
        "X" -> theirMove.defeats?.score ?: SCISSORS.score
        "Y" -> theirMove.score + 3
        "Z" -> theirMove.losesTo().score + 6
        else -> throw IllegalStateException("$theirMove $target")
    }
}

private fun String.theirMove() = mapMove("A", "B", "C")
private fun String.ourMove() = mapMove("X", "Y", "Z")

private fun String.mapMove(rock: String, paper: String, scissors: String) = when (this) {
    rock -> ROCK
    paper -> PAPER
    scissors -> SCISSORS
    else -> throw IllegalStateException(this)
}