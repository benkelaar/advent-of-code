package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.*

class DiagnosticsReport : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val lines = input.nonBlank()
        val counts = lines.map { row -> row.map { it.toString().toInt() } }
            .reduce { acc, row -> acc.zip(row).map { (a, b) -> a + b } }

        val gamma = counts.joinToString("") { (it >= lines.size / 2).toBitString() }.toInt(2)
        val epsilon = gamma xor 0b111111111111

        val oxGen = lines.findMajoritySignal { oneCount, half -> oneCount >= half }
        val co2Scrubber = lines.findMajoritySignal { oneCount, half -> oneCount < half }

        return gamma * epsilon to oxGen * co2Scrubber
    }

    private fun List<String>.findMajoritySignal(test: (Int, Double) -> Boolean): Int {
        var i = 0
        var remaining = this
        while (remaining.size > 1) {
            val oneCount = remaining.count { it[i] == '1' }
            val half = remaining.size / 2.0
            val mostCommonBit = test(oneCount, half).toBit()
            remaining = remaining.filter { it[i] == mostCommonBit }
            i++
        }
        return remaining.only().toInt(2)
    }
}