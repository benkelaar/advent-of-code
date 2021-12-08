package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.only

data class Signal(val signals: Map<Set<Char>, Int>, val output: List<String>) {
    val value get() = output.map { signals[it.toSet()]!! }.joinToString("").toInt()

    companion object {
        fun forLine(line: String): Signal {
            val (signalString, outputString) = line.split(" | ")
            val signals = signalString.split(" ")

            val oneSignal = signals.only { it.length == 2 }
            val sevenSignal = signals.only { it.length == 3 }
            val fourSignal = signals.only { it.length == 4 }
            val eightSignal = signals.only { it.length == 7 }
            val sixSignal = signals.only { s -> s.length == 6 && !oneSignal.all { it in s } }

            val oneList = oneSignal.toList()
            val c = oneList.only { it !in sixSignal }
            val f = oneList.only { it != c }
            val twoSignal = signals.only { f !in it }
            val fiveSignal = signals.only { it.length == 5 && c !in it }
            val threeSignal = signals.only { it.length == 5 && it != twoSignal && it != fiveSignal }

            val nineSignal = signals.only { it.length == 6 && (it.toSet() - threeSignal.toSet()).size == 1 }
            val zeroSignal = signals.only { it.length == 6 && it != sixSignal && it != nineSignal }

            return Signal(
                mapOf(
                    zeroSignal.toSet() to 0,
                    oneSignal.toSet() to 1,
                    twoSignal.toSet() to 2,
                    threeSignal.toSet() to 3,
                    fourSignal.toSet() to 4,
                    fiveSignal.toSet() to 5,
                    sixSignal.toSet() to 6,
                    sevenSignal.toSet() to 7,
                    eightSignal.toSet() to 8,
                    nineSignal.toSet() to 9
                ), outputString.split(" ")
            )
        }

    }
}

class SignalReader : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val nonBlankInput = input.nonBlank()
        val simpleCount = nonBlankInput
            .flatMap { it.split(" | ").last().split(" ") }
            .count { it.length in setOf(2, 3, 4, 7) }
        val signals = nonBlankInput.map { Signal.forLine(it) }
        return simpleCount to signals.sumOf { it.value }
    }
}
