package com.bartenkelaar.year2020.gaming

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nextAfter
import com.bartenkelaar.util.nextFrom
import com.bartenkelaar.util.shiftSo
import com.bartenkelaar.util.tail

class Cups : Solver {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val numbers = input.map { it.toInt() }.toIntArray()

        val simpleEnd = numbers.playRounds(100)
        val simpleScore = simpleEnd.shiftSo(1, 0).tail().joinToString("").toLong()

        val manyNumbers = (numbers.toList() + (numbers.size until 1_000_000)).toIntArray()
        val advancedResult = manyNumbers.playRounds(10_000_000)

        return simpleScore to advancedResult.nextAfter(1).toLong() * advancedResult.nextAfter(1, 2).toLong()
    }

    private fun IntArray.playRounds(rounds: Int): List<Int> {
        for (i in 0 until rounds) {
            if (i % 1000 == 0) println("Calculating round: $i")
            nextRound(i % size)
        }
        return this.toList()
    }

    private fun IntArray.nextRound(currentIndex: Int) {
        val current = get(currentIndex)
        val pickedUp = listOf(get(safe(currentIndex + 1)), get(safe(currentIndex + 2)), get(safe(currentIndex + 3)))
        val target = (current - 4 until current)
            .map { if (it < 1) size + it else it }
            .last { it !in pickedUp }

        var i = safe(currentIndex + 1)
        while (true) {
            val newValue = get(safe(i + 3))
            set(i, newValue)
            if (newValue == target) break
            i = safe(i + 1)
        }
        pickedUp.forEachIndexed { j, e -> set(safe(i + j + 1), e)}
    }

    private fun IntArray.safe(i: Int) = i % size
}

private fun <T> List<T>.nextThree(index: Int) = listOf(nextFrom(index), nextFrom(index, 2), nextFrom(index, 3))
