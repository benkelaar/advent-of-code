package com.bartenkelaar.year2020.gaming

import com.bartenkelaar.Solver
import com.bartenkelaar.util.tail
import com.bartenkelaar.util.zipPerEmpty

class Combat : Solver {
    private val memory = mutableMapOf<Pair<List<Int>, List<Int>>, Boolean>()

    override fun solve(input: List<String>): Pair<Number, Any> {
        val (deck1, deck2) = input.zipPerEmpty()
            .filter { it.isNotEmpty() }
            .map { deck -> deck.filter { card -> card.first().isDigit() }.map { it.toInt() } }

        val (winningDeckSimple, _) = playGame(deck1, deck2,false)
//        val (winningDeckRecursive, _) = playGame(deck1, deck2)
        return winningDeckSimple.score() to 0 // winningDeckRecursive.score()
    }

    private tailrec fun playGame(
        deck1: List<Int>,
        deck2: List<Int>,
        recurse: Boolean = true,
        roundsPlayed: Set<Pair<List<Int>, List<Int>>> = emptySet()
    ): Pair<List<Int>, Boolean> {
        if (deck1.isEmpty()) return deck2 to false
        else if (deck2.isEmpty()) return deck1 to true

        val card1 = deck1.first()
        val card2 = deck2.first()
        val remainingDeck1 = deck1.tail()
        val remainingDeck2 = deck2.tail()

        if (recurse && (remainingDeck1 to remainingDeck2) in roundsPlayed) return deck1 to true

        val player1wins = if (recurse && card1 <= remainingDeck1.size && card2 <= remainingDeck2.size) {
            memoPlay(remainingDeck1.slice(0 until card1), remainingDeck2.slice(0 until card2))
        } else card1 > card2

        return playGame(
            deck1 = if (player1wins) remainingDeck1 + card1 + card2 else remainingDeck1,
            deck2 = if (player1wins) remainingDeck2 else remainingDeck2 + card2 + card1,
            roundsPlayed = roundsPlayed + (remainingDeck1 to remainingDeck2),
            recurse = recurse
        )
    }

    private fun memoPlay(deck1: List<Int>, deck2: List<Int>): Boolean {
        val key = deck1 to deck2
        if (key in memory) return memory.getValue(key)
        val result = playGame(deck1, deck2).second
        memory[key] = result
        return result
    }

    private fun List<Int>.score() = mapIndexed { i, c -> (size - i) * c }.sum()
}