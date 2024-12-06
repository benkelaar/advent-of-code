package com.bartenkelaar.year2023.gaming

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.splitWhiteSpace
import com.bartenkelaar.util.tail
import com.bartenkelaar.util.toIntList
import kotlin.math.pow

private data class ScratchCard(
    val cardNumber: Int,
    val winners: Set<Int>,
    val numbers: Set<Int>,
) {
    val winNumberCount = numbers.count { it in winners }

    fun score() = if (winNumberCount == 0) 0.0 else 2.0.pow(winNumberCount - 1.0)
}

class ScratchCardCounter : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val cards = input
            .nonBlank()
            .map { it.split("(: )|( \\| )".toRegex()) }
            .map { (number, winString, numberString) ->
                ScratchCard(
                    number.trim('C', 'a', 'r', 'd', ' ').toInt(),
                    winString.toIntSet(),
                    numberString.toIntSet(),
                )
            }

        return cards.sumOf { it.score() } to copyCount(cards, mapOf(cards.size to 1), 0)
    }

    private tailrec fun copyCount(cards: List<ScratchCard>, copies: Map<Int, Int>, count: Int): Int {
        if (cards.isEmpty()) return count
        val newCopies = copies.filter { (k, _) -> k != 0 }.mapKeys { (k, _) -> k - 1 }
        val cardCopies = newCopies.values.sum()
        val winCount = cards.first().winNumberCount

        return copyCount(
            cards = cards.tail(),
            copies = newCopies + mapOf(winCount to newCopies.getOrDefault(winCount, 0) + cardCopies),
            count = count + cardCopies,
        )
    }

    private fun String.toIntSet() = trim().splitWhiteSpace().toIntList().toSet()
}
