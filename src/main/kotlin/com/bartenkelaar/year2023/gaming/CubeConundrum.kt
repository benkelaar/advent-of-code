package com.bartenkelaar.year2023.gaming

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.productOf

private enum class CubeColour(val available: Int) {
    RED(12),
    GREEN(13),
    BLUE(14),
}

private data class Cubes(val amount: Int, val colour: CubeColour) {
    companion object {
        fun fromString(cubeString: String): Cubes {
            val (a, c) = cubeString.split(" ")
            return Cubes(a.toInt(), CubeColour.valueOf(c.uppercase()))
        }
    }
}

private data class Game(val id: Int, val handsShown: List<Set<Cubes>>) {
    fun isPossible() = CubeColour.entries.all { maxShown(it) <= it.available }

    fun power() = CubeColour.entries.productOf { maxShown(it) }

    private fun maxShown(colour: CubeColour) = handsShown.maxOf { hand -> hand.find { it.colour == colour }?.amount ?: 0 }
}

class CubeConundrum : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val games = input
            .nonBlank()
            .map { it.substring(5).split(": ") }
            .map { (id, rest) -> Game(id.toInt(), readHandsShown(rest)) }
        return games.filter { it.isPossible() }.sumOf { it.id } to games.sumOf { it.power() }
    }

    private fun readHandsShown(handsString: String): List<Set<Cubes>> {
        val hands = handsString.split("; ")
        return hands.map { it.split(", ").map(Cubes::fromString).toSet() }
    }
}
