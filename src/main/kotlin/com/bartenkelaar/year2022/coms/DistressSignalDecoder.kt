package com.bartenkelaar.year2022.coms

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.spindexOf
import com.bartenkelaar.util.zipPerEmpty

private sealed interface Signal : Comparable<Signal> {
    val stringSize: Int
}

private data class Many(val values: List<Signal>) : Signal {
    constructor(vararg values: Signal) : this(values.toList())

    override val stringSize = values.sumOf { it.stringSize } + maxOf(values.size - 1, 0) + 2

    override fun compareTo(other: Signal): Int = when (other) {
        is Digit -> compareTo(Many(other))
        is Many -> {
            var compared = 0
            var i = 0
            while (i <= values.lastIndex && compared == 0) {
                compared = if (i > other.values.lastIndex) 1 else values[i].compareTo(other.values[i++])
            }
            if (compared == 0 && other.values.size > values.size) -1 else compared
        }
    }

    companion object {
        fun forString(signal: String): Many {
            try {
                return Many(listString(signal))
            }
            catch (exception: Exception) {
                println("Wrong signal: $signal")
                throw exception
            }
        }

        private fun listString(signal: String, list: List<Signal> = emptyList()): List<Signal> =
            when {
                signal.isEmpty() || signal.first() == ']' -> list
                signal.first() == ',' -> listString(signal.substring(1), list)
                signal.first().isDigit() -> {
                    val digitEnd = signal.indexOfFirst { !it.isDigit() }
                    listString(
                        signal = signal.substring(digitEnd),
                        list = list + Digit(signal.substring(0, digitEnd).toInt())
                    )
                }
                else -> {
                    val subList = Many.forString(signal.substring(1))
                    listString(signal.substring(subList.stringSize), list + subList)
                }
            }
    }
}

private data class Digit(val value: Int) : Signal {
    override val stringSize = value.toString().length

    override fun compareTo(other: Signal) = when (other) {
        is Many -> Many(this).compareTo(other)
        is Digit -> value.compareTo(other.value)
    }
}

class DistressSignalDecoder : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val pairedPackets = input.zipPerEmpty().map { pair -> pair.map { Many.forString(it) } }

        val sortedPairIndices = pairedPackets.mapIndexedNotNull { i, (left, right) -> (i + 1).takeIf { left < right } }

        val divider2 = divider(2)
        val divider6 = divider(6)
        val allSignals = (pairedPackets.flatten() + listOf(divider2, divider6)).sorted()
        val decoderKey = allSignals.spindexOf(divider2) * allSignals.spindexOf(divider6)

        return sortedPairIndices.sum() to decoderKey
    }

    private fun divider(value: Int) = Many(Many(Digit(value)))
}
