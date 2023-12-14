package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.of

class MirrorBender : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val rows = input.nonBlank()
        return northBeamLoad(rows) to 0
    }

    private fun northBeamLoad(rows: List<String>): Int {
        val loads = (rows.first().length of rows.size).toMutableList()
        var totalLoad = 0
        rows.forEachIndexed { y, row ->
            loads.forEachIndexed { x, l ->
                when (row[x]) {
                    'O' -> {
                        totalLoad += l
                        loads[x]--
                    }
                    '#' -> loads[x] = rows.lastIndex - y
                }
            }
        }

        return totalLoad
    }
}