package com.bartenkelaar.year2020.encryption

import com.bartenkelaar.Solver
import com.bartenkelaar.year2020.expenses.SumChecker

class XmasBreaker(private val sumChecker: SumChecker) : Solver {
    override fun solve(input: List<String>): Pair<Long, Number> {
        val numbers = input.filter { it.isNotBlank() }.map { it.toLong() }
        val (key, preamble) = findKey(numbers)
        val weakness = findWeakness(key, numbers)
        return key to weakness
    }

    private fun findWeakness(key: Long, numbers: List<Long>): Long {
        (0..numbers.size).forEach {
            var sum = 0L
            var offset = 0
            while (sum < key) {
                sum += numbers[it + offset]
                offset++
            }

            if (sum == key) {
                val range = numbers.slice(it..it + offset).sorted()
                return range.first() + range.last()
            }
        }
        return 0
    }

    private fun findKey(numbers: List<Long>): Pair<Long, Set<Long>> {
        val preamble = numbers.slice(0..24).toMutableSet()

        numbers.slice(25..numbers.lastIndex).forEach {
            val pair = sumChecker.findPairSum(preamble.toSet(), it)
            if (pair == null) return it to preamble.toSet()
            else preamble.add(it)
        }

        return 0L to emptySet()
    }

}