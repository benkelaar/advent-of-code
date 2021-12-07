package com.bartenkelaar.year2015.code

import com.bartenkelaar.util.Solver

@JvmInline
value class Password(private val password: String) {
    fun isValid() = hasTwoDoubles() && hasTriplet()

    fun rotate() = Password(password.rotate())

    override fun toString() = password

    private fun String.rotate(): String =
        if (last() == 'z') substring(0, lastIndex).rotate() + 'a'
        else substring(0, lastIndex) + last().rotate()

    private fun Char.rotate() = this + if (this in setOf('h', 'n', 'k')) 2 else 1
    private fun hasTwoDoubles() = password.split("""([a-z])\1""".toRegex()).size > 2
    private fun hasTriplet() = password.windowed(3) { it[2] - it[0] == 2 && it[1] - it[0] == 1 }.any { it }
}

class PasswordIncrementer : Solver() {
    override fun solve(input: List<String>): Pair<String, Any> {
        val password = Password(input.first())
        val firstPassword = findNexValidPassword(password)
        val secondPassword = findNexValidPassword(firstPassword.rotate())
        return firstPassword.toString() to secondPassword.toString()
    }

    private fun findNexValidPassword(password: Password): Password {
        var attempt = password
        while (!attempt.isValid()) attempt = attempt.rotate()
        return attempt
    }
}
