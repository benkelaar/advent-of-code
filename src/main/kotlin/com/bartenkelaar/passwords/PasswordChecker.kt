package com.bartenkelaar.passwords

import com.bartenkelaar.Solver

class PasswordChecker : Solver<Int> {
    override fun solve(input: List<String>) = input.mapNotNull(::parseLine).filter(PasswordLine::isValid).count()
}

val LINE_REGEX = Regex("(\\d+)-(\\d+) ([a-z]): (\\w+)")

fun parseLine(line: String): PasswordLine? {
    val match = LINE_REGEX.matchEntire(line)
    return match?.let {
        val (_, lowBound, highBound, char, password) = it.groupValues
        PasswordLine(lowBound.toInt(), highBound.toInt(), char[0], password)
    }
}

data class PasswordLine(
    val lowBound: Int,
    val highBound: Int,
    val char: Char,
    val password: String
) {
    fun isValid() = password.count { it == char } in lowBound .. highBound
}