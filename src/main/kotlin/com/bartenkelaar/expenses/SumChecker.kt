package com.bartenkelaar.expenses

class SumChecker {
    fun findPairSum(entries: List<Int>): Pair<Int, Int>? {
        for (entry in entries) {
            val partner = 2020 - entry
            if (partner in entries) return entry to partner
        }
        return null;
    }
}
