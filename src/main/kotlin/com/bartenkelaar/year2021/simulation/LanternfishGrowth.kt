package com.bartenkelaar.year2021.simulation

import com.bartenkelaar.util.Solver

class LanternfishGrowth : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val ages = input.first().split(',').map { it.toInt() }

        val earlyFishCount = runSimulation(ages, 80)
        val lateFishCount = runSimulation(ages, 256)

        return earlyFishCount to lateFishCount
    }

    private fun runSimulation(ages: List<Int>, cycles: Int): Long {
        var ageCounts = (0..8).map { age -> ages.count { it == age } }.map { it.toLong() }
        for (i in 0 until cycles) {
            ageCounts = (ageCounts.subList(1, ageCounts.size) + ageCounts.first()).toMutableList()
            ageCounts[6] += ageCounts.last()
        }
        return ageCounts.sum()
    }
}