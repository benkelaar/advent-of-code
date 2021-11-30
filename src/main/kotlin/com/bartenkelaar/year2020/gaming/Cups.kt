package com.bartenkelaar.year2020.gaming

import com.bartenkelaar.Solver
import com.bartenkelaar.util.only
import java.lang.StringBuilder

data class Cup(val value: Int, var next: Cup? = null, var previous: Cup? = null) {
    fun nextThree() = listOf(next!!, next!!.next!!, next!!.next!!.next!!)

    fun linkTo(cup: Cup) {
        next = cup
        cup.previous = this
    }

    override fun toString(): String {
        val result = StringBuilder("")
        var next = next!!
        while (next.value != value) {
            result.append(next.value)
            next = next.next!!
        }
        return result.toString()
    }
}

class Cups : Solver {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val numbers = input.map { it.toInt() }

        val simpleCups = numbers.toCups()
        playRounds(simpleCups.first(), 100, simpleCups.size)
        val simpleScore = simpleCups.findLast { it.value == 1 }.toString()

//        val manyCups = (numbers.toList() + (numbers.size until 1_000_000)).toCups()
//        playRounds(manyCups.first(), 10_000_000, manyCups.size)
//        val oneCup = manyCups.filter { it.value == 1 }.only()

        return simpleScore.toLong() to 0 // oneCup.next!!.value.toLong() * oneCup.next!!.next!!.value.toLong()
    }

    private fun List<Int>.toCups(): List<Cup> {
        val cups = map(::Cup)
        cups.forEachIndexed { i, cup ->
            cup.next = cups[(i + 1) % size]
            cup.next!!.previous = cup
        }
        return cups
    }

    private fun playRounds(startCup: Cup, rounds: Int, maxCupValue: Int) {
        var currentCup = startCup
        for (i in 0 until rounds) {
            if (i % 10_000 == 0) println("Round played: $i")
            val nextThree = currentCup.nextThree()
            currentCup.linkTo(nextThree.last().next!!)

            val targetCup = findTargetCup(currentCup, nextThree, maxCupValue)
            nextThree.last().linkTo(targetCup.next!!)
            targetCup.linkTo(nextThree.first())

            currentCup = currentCup.next!!
        }
    }

    private fun findTargetCup(
        currentCup: Cup,
        nextThree: List<Cup>,
        maxCupValue: Int
    ): Cup {
        val target = (currentCup.value - 4 until currentCup.value)
            .map { if (it < 1) maxCupValue + it else it }
            .last { it !in nextThree.map { it.value } }
        var targetCup = currentCup.previous!!
        while (targetCup.value != target) {
            targetCup = targetCup.previous!!
        }
        return targetCup
    }
}