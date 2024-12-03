package com.bartenkelaar.year2015.delivery

import com.bartenkelaar.util.Solver

class ElfDeliveryCounter : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val target = input.first().toInt()

        return deliverySum(target, 10, false) to deliverySum(target, 11, true)
    }

    private fun deliverySum(
        target: Int,
        packagesPerAddress: Int,
        limited: Boolean,
    ): Int {
        var i = if (limited) 704999 else 664999
        do {
            val packages = (++i).sumOfFactors(limited) * packagesPerAddress
            if (i % 1000 == 0) println("$i: $packages")
        } while (packages <= target)
        return i
    }

    private fun Int.sumOfFactors(limited: Boolean): Int {
        val base = this + 1
        val start = if (limited) this / 50 else 2
        val factors = (start until this).filter { this % it == 0 }
        return base + factors.sum()
    }
}
