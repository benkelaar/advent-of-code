package com.bartenkelaar.expenses

class SumChecker {
    fun findPairSum(entries: Set<Int>) = findPairSum(entries, 2020)

    private fun findPairSum(entries: Set<Int>, target: Int): Pair<Int, Int>? {
        for (entry in entries) {
            val partner = target - entry
            if (partner != entry && partner in entries) return entry to partner
        }
        return null;
    }

    fun findTripleSum(entries: Set<Int>): List<Int> {
        for (entry in entries) {
            val target = 2020 - entry
            findPairSum(entries, target)?.let { return listOf(entry, it.first, it.second) }
        }
        return emptyList()
    }
}
