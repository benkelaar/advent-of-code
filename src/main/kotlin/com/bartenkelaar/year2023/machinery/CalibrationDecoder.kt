package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.reverseAll
import com.bartenkelaar.util.spindexOf

class CalibrationDecoder : Solver() {
    private val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    override fun solve(input: List<String>): Pair<Any, Any> {
        val nonBlankInput = input.nonBlank()
        val firstDigits = nonBlankInput.map { it.firstDigit() }
        val firstSpelledOutDigits = nonBlankInput.map(firstSpelledOutDigit(numbers))

        val reversedInput = nonBlankInput.reverseAll()
        val lastDigits = reversedInput.map { it.firstDigit() }
        val lastSpelledOutDigits = reversedInput.map(firstSpelledOutDigit(numbers.reverseAll()))

        val digitSum = sumDigits(firstDigits, lastDigits)
        val spelledOutDigitSum = sumDigits(firstSpelledOutDigits, lastSpelledOutDigits)
        return digitSum to spelledOutDigitSum
    }

    private fun String.firstDigit() = find { it.isDigit() }!!.digitToInt()

    private fun firstSpelledOutDigit(numbers: List<String>): (String) -> Int {
        val regex = "(${numbers.joinToString("|")}|\\d)".toRegex()
        return {
            val value = regex.find(it)!!.value
            if (value.first().isDigit()) value.toInt() else numbers.spindexOf(value)
        }
    }

    private fun sumDigits(firstDigits: List<Int>, lastDigits: List<Int>) =
        firstDigits.zip(lastDigits).sumOf { (first, last) -> "$first$last".toInt() }
}
