package com.bartenkelaar.year2020.passwords

import com.bartenkelaar.util.Solver

class PasswordChecker : Solver() {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val lines = input.mapNotNull(::parseLine)
        val validSledRentalPasswords = lines.count { it.isValidSledRental() }
        val validTobogganPasswords = lines.count { it.isValidToboggan() }
        return validSledRentalPasswords to validTobogganPasswords
    }
}

val LINE_REGEX = """(\d+)-(\d+) ([a-z]): (\w+)""".toRegex()

fun parseLine(line: String): PasswordLine? {
    val match = LINE_REGEX.matchEntire(line)
    return match?.let {
        val (lowBound, highBound, char, password) = it.destructured
        PasswordLine(lowBound.toInt(), highBound.toInt(), char[0], password)
    }
}

data class PasswordLine(
    val lowNumber: Int,
    val highNumber: Int,
    val char: Char,
    val password: String
) {
    fun isValidSledRental() = password.count { it == char } in lowNumber .. highNumber

    fun isValidToboggan() = (charAt(lowNumber) == char) xor (charAt(highNumber) == char)

    private fun charAt(charNumber: Int) = password[charNumber - 1]
}