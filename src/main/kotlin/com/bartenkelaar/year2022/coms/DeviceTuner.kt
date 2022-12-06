package com.bartenkelaar.year2022.coms

import com.bartenkelaar.util.Solver

class DeviceTuner : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> = with(input.first()) {
        findFirstUniqueRangeIndex(4) to findFirstUniqueRangeIndex(14);
    }

    private fun String.findFirstUniqueRangeIndex(rangeSize: Int): Int {
        substring(rangeSize).scanIndexed(substring(0 until rangeSize)) { index, mark, c ->
            if (mark.toSet().size == rangeSize) return index + rangeSize
            mark.substring(1 until rangeSize) + c
        }
        return -1
    }
}