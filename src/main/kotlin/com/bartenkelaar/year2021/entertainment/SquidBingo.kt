package com.bartenkelaar.year2021.entertainment

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.pivot

data class Board(private val rows: List<List<Int>>) {
    private val columns = rows.pivot()

    fun hasWonFor(calledNumbers: Set<Int>) =
        rows.any { calledNumbers.containsAll(it) } || columns.any { calledNumbers.containsAll(it) }

    fun score(calledNumbers: Set<Int>) = (rows.flatten() - calledNumbers).sum()

    companion object {
        fun forLines(lines: List<String>): Board {
            require(lines.nonBlank().size == 5)
            val rows = lines.map { row ->
                row.trim()
                    .split("\\s+".toRegex())
                    .map { it.toInt() }
            }
            return Board(rows)
        }
    }
}

class SquidBingo : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val numbers = input.first().split(",").map { it.toInt() }
        val boards = input.subList(1, input.size).nonBlank()
            .windowed(5)
            .map { Board.forLines(it) }

        var i = 4
        val mentionedNumbers = numbers.subList(0, 4).toMutableList()
        while(!boards.any { it.hasWonFor(mentionedNumbers.toSet()) }) {
            mentionedNumbers += numbers[i++]
        }
        val winners = boards.filter { it.hasWonFor(mentionedNumbers.toSet()) }
        val winner = winners.first()
        val firstWinnerNumber = mentionedNumbers.last()
        val winnerScore = winner.score(mentionedNumbers.toSet())

        while(!boards.all { it.hasWonFor(mentionedNumbers.toSet()) }) {
            mentionedNumbers += numbers[i++]
        }
        val lastWinners = boards.filter { !it.hasWonFor(mentionedNumbers.subList(0, mentionedNumbers.lastIndex).toSet()) }
        val lastWinner = lastWinners.last()

        return winnerScore * firstWinnerNumber to lastWinner.score(mentionedNumbers.toSet()) * mentionedNumbers.last()
    }
}