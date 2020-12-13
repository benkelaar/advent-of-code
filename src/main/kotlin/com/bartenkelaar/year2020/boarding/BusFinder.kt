package com.bartenkelaar.year2020.boarding

import com.bartenkelaar.Solver

class BusFinder : Solver {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val timestamp = input.first().trim().toLong()
        val busIndexes = input[1].trim().split(",")
        val busIds = busIndexes.filter { it != "x" }.map { it.toLong() }

        val busOffsets = busIds.zip(busIds.map { busIndexes.indexOf(it.toString()) % it }).toMap()
        val departureMinutes = busIds.map { it.offSetModulo(timestamp) }
        val earliest = departureMinutes.minOrNull()!!
        val earliestBusId = busIds[departureMinutes.indexOf(earliest)]

        val start = findConsecutiveDepartureTime(busIds, busOffsets)

        return (earliest * earliestBusId) to start
    }
}

fun findConsecutiveDepartureTime(ids: List<Long>, busOffsets: Map<Long, Long>): Long {
    val matched = ids.slice(0..1).toMutableList()
    var increment = matched.first()
    var timestamp = increment - busOffsets.getValue(increment)

    while (true) {
        if (!matched.all { it.offSetModulo(timestamp) == busOffsets.getValue(it) }) {
            timestamp += increment
        } else if (matched.size != ids.size) {
            increment *= if (matched.last() % increment == 0L) 1 else matched.last()
            matched += (ids - matched).first()
        } else {
            break;
        }
    }
    return timestamp
}

@Suppress("unused")
fun findConsecutiveDepartureTimeBruteForce(ids: List<Long>, busOffsets: Map<Long, Long>): Long {
    val maxId = ids.maxOrNull()!!

    var testValue: Long = (maxId - busOffsets.getValue(maxId))
    while (!busOffsets.all { it.key.offSetModulo(testValue) == it.value }) {
        testValue += maxId
    }
    return testValue
}

private fun Long.offSetModulo(other: Long) = (this - (other % this)) % this
