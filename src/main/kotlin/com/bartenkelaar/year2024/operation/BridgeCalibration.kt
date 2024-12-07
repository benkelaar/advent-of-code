package com.bartenkelaar.year2024.operation

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.toLongList

data class Sum(val result: Long, val values: List<Long>) {
    fun canBeDone(cache: Map<Int, Set<List<(Long, Long) -> Long>>>): Boolean {
        val permutations = cache[values.size - 1]!!
        for (order in permutations) {
            val orderWorks = tryOperationOrder(order)
            if (orderWorks) return true
        }
        return false
    }

    private fun tryOperationOrder(operations: List<(Long, Long) -> Long>): Boolean {
        val numbers = values.iterator()
        var sum = numbers.next()
        for (operation in operations) {
            sum = operation(sum, numbers.next())
            if (sum > result) return false
        }
        return sum == result
    }
}

class BridgeCalibration : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val calibrationSums = input
            .nonBlank()
            .map { it.split(": ".toRegex()) }
            .map { (r, vs) -> Sum(r.toLong(), vs.toLongList()) }
        val maxOperations = calibrationSums.maxOf { it.values.size - 1 }

        val options = setOf<(Long, Long) -> Long>(Long::plus, Long::times)
        val permutationCache = calculatePermutations(maxOperations, options)
        val totalCalibrationResult = calibrationSums.filter { it.canBeDone(permutationCache) }.sumOf { it.result }

        val threeOptions = options + { a, b -> "$a$b".toLong() }
        val threePermutationCache = calculatePermutations(maxOperations, threeOptions)
        val threeCalibrationResult = calibrationSums.filter { it.canBeDone(threePermutationCache) }.sumOf { it.result }
        return totalCalibrationResult to threeCalibrationResult
    }

    private fun calculatePermutations(maxOperations: Int, options: Set<(Long, Long) -> Long>): Map<Int, Set<List<(Long, Long) -> Long>>> {
        val permutationCache = mutableMapOf(1 to options.map { listOf(it) }.toSet())
        for (operationCount in 2..maxOperations) {
            permutationCache[operationCount] =
                options.flatMap { new -> permutationCache[operationCount - 1]!!.map { it + new } }.toSet()
        }
        return permutationCache.toMap()
    }
}
