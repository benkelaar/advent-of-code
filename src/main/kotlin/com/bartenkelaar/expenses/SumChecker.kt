package com.bartenkelaar.expenses

class SumChecker {
    fun findPairSum(entries: Set<Int>) = findPairSum(entries, 2020)

    private fun findPairSum(entries: Set<Int>, target: Int): Pair<Int, Int>? {
        val sorted = entries.sorted()

        tailrec fun findPairRecursive(lowIndex: Int, highIndex: Int): Pair<Int, Int>? {
            if (lowIndex == highIndex) return null
            val low = sorted[lowIndex]
            val high = sorted[highIndex]
            val sum = low + high

            return if (sum == target) (low to high)
            else findPairRecursive(
                lowIndex = if (sum < target) lowIndex + 1 else lowIndex,
                highIndex = if (sum > target ) highIndex - 1 else highIndex,
            )
        }

        return findPairRecursive(0, entries.size - 1);
    }


    fun findTripleSum(entries: Set<Int>): List<Int> {
        for (entry in entries) {
            val target = 2020 - entry
            findPairSum(entries, target)?.let { return listOf(entry, it.first, it.second) }
        }
        return emptyList()
    }
}
