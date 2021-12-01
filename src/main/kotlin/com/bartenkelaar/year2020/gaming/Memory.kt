package com.bartenkelaar.year2020.gaming

import com.bartenkelaar.util.Solver

class Memory(private val number: Int) : Solver() {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val startingNumbers = input.map { it.toInt() }
        val twentyTwentyNumber = findNumber(startingNumbers, number)
        val maniedNumber = 0 // findNumber(startingNumbers, 30000000)
        return twentyTwentyNumber to maniedNumber
    }

    private fun findNumber(startingNumbers: List<Int>, target: Int): Int {
        val indexMap = startingNumbers.mapIndexed { i, n -> n to i }.toMap().toMutableMap()
        var lastIndex = startingNumbers.lastIndex
        var lastNumber = startingNumbers[lastIndex]
        while (lastIndex < target - 1) {
            val newNumber = if (lastNumber !in indexMap) 0 else lastIndex - indexMap.getValue(lastNumber)
            indexMap[lastNumber] = lastIndex++
            lastNumber = newNumber
        }
        return lastNumber
    }
}