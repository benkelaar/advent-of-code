package com.bartenkelaar.year2023.farming

import com.bartenkelaar.util.*
import kotlin.math.min

class AlmanacReader : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val seeds = input.first.substring(7).toLongList()
        val segments = input.tail().zipPerEmpty().map { it.tail().map { row ->
            row.toLongList().let { (dStart, sStart, length) -> sStart ..< sStart + length to dStart } }
        }

        val (starts, lengths) = seeds.mapIndexed { i, l -> i to l }.partition { (i, _) -> i % 2 == 0 }
        val seedRanges = starts.map { (_, s) -> s }.zip(lengths.map { (_, l) -> l }).map { (s, l) -> s ..< s + l }

        return almanacMap(seeds, segments).min() to almanacRangeMap(seedRanges, segments).minOf { it.first }
    }

    private tailrec fun almanacMap(input: List<Long>, segments: List<List<Pair<LongRange, Long>>>): List<Long> {
        if (segments.isEmpty()) return input
        val ranges = segments.first
        return almanacMap(
            input = input.map { i -> ranges.find { i in it.first }?.map(i) ?: i },
            segments = segments.tail()
        )
    }

    private fun Pair<LongRange, Long>.map(l: Long) = l - first.first + second

    private tailrec fun almanacRangeMap(input: List<LongRange>, segments: List<List<Pair<LongRange, Long>>>): List<LongRange> {
        if (segments.isEmpty()) return input
        val ranges = segments.first
        return almanacRangeMap(input.flatMap { range ->
            val startRange = ranges.find { (r, _) -> range.first in r }
            val mapRanges = ranges.filter { (r, _) -> r.first in range }.sortedBy { (r, _) -> r.first }

            val firstRange = startRange?.let { it.first to it.map(range.first)..it.map(min(it.first.last, range.last)) }
            val lastRange = (mapRanges.lastOrNull() ?: firstRange)?.first?.takeIf { it.last + 1 in range }
                ?.let { it.last + 1..range.last() }?.let { it to it }
            val mappedRanges = mapRanges.map { mapping ->
                val start = mapping.first.first
                val end = min(mapping.first.last, range.last)
                mapping.first to mapping.map(start)..mapping.map(end)
            }
            val allMappedRanges = listOfNotNull(firstRange) + mappedRanges + listOfNotNull(lastRange)
            val prefix = allMappedRanges.firstOrNull()?.takeIf { it.first.first > range.first }
                ?.let { range.first..<it.first.first }
            (listOfNotNull(prefix) + allMappedRanges.zipWithNext().flatMap { (mappedRange, nextMappedRange) ->
                listOfNotNull(
                    mappedRange.second,
                    nextMappedRange.first.takeIf { it.first > mappedRange.first.last + 1 }?.let {
                        mappedRange.first.last + 1..<it.first
                    })
            } + listOfNotNull(allMappedRanges.lastOrNull()?.second)).takeIf { it.isNotEmpty() } ?: listOf(range)
        }, segments.tail())
    }
}