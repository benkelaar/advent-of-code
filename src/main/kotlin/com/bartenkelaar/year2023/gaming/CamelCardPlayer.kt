package com.bartenkelaar.year2023.gaming

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.sumNotNullIndexed

private enum class HandType(private val groups: List<Int>) {
    FIVE_OF_A_KIND(5),
    FOUR_OF_A_KIND(4),
    FULL_HOUSE(3, 2),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2, 2),
    PAIR(2),
    HIGH_CARD(1),
    ;

    constructor(vararg matches: Int) : this(matches.toList())

    companion object {
        private fun findFor(charCounts: Map<Char, Int>): HandType =
            entries.first { type ->
                val groupChars = mutableListOf<Char?>()
                for (group in type.groups) {
                    groupChars += charCounts.firstNotNullOfOrNull { (char, count) ->
                        char.takeIf { count == group && it !in groupChars }
                    }
                }
                groupChars.all { it != null }
            }

        fun findFor(hand: String) = findFor(hand.groupBy { it }.mapValues { (_, v) -> v.size })

        fun findForJokered(hand: String): HandType {
            val counts = hand.groupBy { it }.mapValues { (_, v) -> v.size }
            val jokerCount = counts.getOrDefault('J', 0)
            if (jokerCount == 5) return FIVE_OF_A_KIND
            val otherCounts = counts.filterKeys { it != 'J' }
            val highestChar = otherCounts.entries
                .sortedByDescending { (_, v) -> v }
                .first()
                .key
            val charCounts = otherCounts + mapOf(highestChar to otherCounts.getValue(highestChar) + jokerCount)
            return findFor(charCounts)
        }
    }
}

private val simpleCardOrder = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
private val jokerCardOrder = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

private data class Hand(
    val string: String,
    val type: HandType,
    val bid: Int,
)

private class HandComparator(val cardOrder: List<Char>) : Comparator<Hand> {
    override fun compare(o1: Hand, o2: Hand): Int {
        val typeOrder = HandType.entries
        val typeCompare = typeOrder.indexOf(o2.type).compareTo(typeOrder.indexOf(o1.type))
        if (typeCompare != 0) return typeCompare
        val firstDiff = o2.string.indices.firstOrNull { o2.string[it] != o1.string[it] } ?: 0
        return cardOrder.indexOf(o2.string[firstDiff]).compareTo(cardOrder.indexOf(o1.string[firstDiff]))
    }
}

class CamelCardPlayer : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val inputNumbers = input.nonBlank().map { it.split(' ') }
        val simpleHands = inputNumbers.toSortedHands(simpleCardOrder, HandType::findFor)
        val jokerHands = inputNumbers.toSortedHands(jokerCardOrder, HandType::findForJokered)

        return simpleHands.sumNotNullIndexed(::winnings) to jokerHands.sumNotNullIndexed(::winnings)
    }

    private fun List<List<String>>.toSortedHands(cardOrder: List<Char>, typer: (String) -> HandType) =
        map { (hand, bid) -> Hand(hand, typer(hand), bid.toInt()) }.sortedWith(HandComparator(cardOrder))

    private fun winnings(index: Int, hand: Hand) = (index + 1) * hand.bid
}
