package com.bartenkelaar.year2020.expenses

class SumChecker {
    fun findPairSum(entries: Set<Long>, target: Long = 2020): Pair<Long, Long>? {
        val sorted = entries.sorted()

        tailrec fun findPairRecursive(lowIndex: Int, highIndex: Int): Pair<Long, Long>? {
            if (lowIndex == highIndex) return null
            val low = sorted[lowIndex]
            val high = sorted[highIndex]
            val sum = low + high

            return if (sum == target) (low to high)
            else findPairRecursive(
                lowIndex = if (sum < target) lowIndex + 1 else lowIndex,
                highIndex = if (sum > target) highIndex - 1 else highIndex
            )
        }

        return findPairRecursive(0, sorted.lastIndex)
    }


    fun findTripleSum(entries: Set<Long>): List<Long> {
        for (entry in entries) {
            val target = 2020 - entry
            findPairSum(entries, target)?.let { return listOf(entry, it.first, it.second) }
        }
        return emptyList()
    }
}
