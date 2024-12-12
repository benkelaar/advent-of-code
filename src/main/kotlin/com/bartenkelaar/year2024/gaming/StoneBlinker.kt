package com.bartenkelaar.year2024.gaming

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.toLongList

class StoneBlinker(private val blinks: Int = 25) : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        var stones = input.first().toLongList()

        val cache = mutableMapOf<Pair<Long, Int>, Long>()
        val count = stones.sumOf { countStonesRecursive(it, blinks, cache) }
        val megaCount = stones.sumOf { countStonesRecursive(it, 75, cache) }

        return count to megaCount
    }

    private fun countStonesRecursive(stone: Long, blinks: Int, cache: MutableMap<Pair<Long, Int>, Long>): Long {
        if (blinks == 0) return 1
        if ((stone to blinks) in cache) return cache.getValue(stone to blinks)

        val nextBLink = blinks - 1
        val s = stone.toString()
        val count = when {
            stone == 0L -> countStonesRecursive(1L, nextBLink, cache)
            s.length % 2 == 0 -> s.stoneSplit().sumOf { countStonesRecursive(it.toLong(), nextBLink, cache) }
            else -> countStonesRecursive(stone * 2024, nextBLink, cache)
        }

        cache[stone to blinks] = count
        return count
    }

    private fun String.stoneSplit() = listOf(substring(0, length / 2), substring(length / 2, length))
}
