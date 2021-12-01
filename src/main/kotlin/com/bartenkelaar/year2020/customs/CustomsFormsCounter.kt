package com.bartenkelaar.year2020.customs

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.zipPerEmpty

class CustomsFormsCounter : Solver() {
    override fun solve(input: List<String>): Pair<Int, Int> {
        val groups = input.zipPerEmpty()
        return groups.map { it.countUniqueCharacters() }.sum() to
                groups.map { it.countUbiquitousCharacters() }.sum()
    }
}

private fun List<String>.countUbiquitousCharacters() =
    first().toCharArray().count { char -> all { string -> char in string } }

private fun List<String>.countUniqueCharacters() = flatMap { it.toCharArray().toList() }.toSet().size

