package com.bartenkelaar.year2020.passports

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank

class PassportChecker : Solver {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val passports = input.fold(PassPortBatch(), PassPortBatch::addLine)
        return passports.countValidPassports() to passports.countDataValidPassports()
    }

}

private val REQUIRED_KEYS = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
private val VALID_EYE_COLOURS = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

data class PassPort(
    val data: Map<String, String>
) {
    fun isValid() = REQUIRED_KEYS.all(data::containsKey)

    fun isDataValid() = isValid() &&
            data.getValue("byr").toInt() in 1920 .. 2002 &&
            data.getValue("iyr").toInt() in 2010 .. 2020 &&
            data.getValue("eyr").toInt() in 2020 .. 2030 &&
            data.getValue("hgt").matches("(1([5-8][0-9]|9[0-3])cm|(59|6[0-9]|7[0-6])in)".toRegex()) &&
            data.getValue("hcl").matches("#[0-9a-f]{6}".toRegex()) &&
            data.getValue("ecl") in VALID_EYE_COLOURS &&
            data.getValue("pid").matches("""\d{9}""".toRegex())


    companion object {
        fun forLines(lines: List<String>) = PassPort(
            lines.flatMap { it.split(" ") }
                .nonBlank()
                .map { it.trim().split(":").zipWithNext().first() }
                .toMap()
        )
    }
}

class PassPortBatch {
    private val list = mutableListOf<PassPort>()

    private val lineBuffer = mutableListOf<String>()
    fun addLine(line: String): PassPortBatch {
        if (line.isBlank()) {
            list += PassPort.forLines(lineBuffer.toList())
            lineBuffer.clear()
        } else lineBuffer += line
        return this
    }

    fun countValidPassports() = list.count { it.isValid() }
    fun countDataValidPassports() = list.count { it.isDataValid() }
}
