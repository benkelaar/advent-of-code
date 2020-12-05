package com.bartenkelaar.year2020.boarding

import com.bartenkelaar.Solver
import kotlin.math.pow

class SeatFinder : Solver {
    override fun solve(input: List<String>) =
        input.filter { it.isNotBlank() }
            .map { code ->
            code.map { it.toBinary() }
                .joinToString("")
                .toInt(2)
        }.maxOrNull()!! to 0
}

data class Seat(
    val row: Int,
    val column: Int,
) {
    companion object {
        fun parseFrom(code: String): Seat {
            val rowPart = code.slice(0..6)
            val columnPart = code.slice(7..code.lastIndex)
            return Seat(
                row = rowPart.toBinaryIndex(),
                column = columnPart.toBinaryIndex()
            )
        }

        private fun String.toBinaryIndex() =
            map { it.toBinary() }
                .mapIndexed { i, b -> b.toBInt() * 2.pow(this.lastIndex - i) }
                .sum()
    }
}

private fun Int.pow(n: Int) = toDouble().pow(n).toInt()
private fun Char.toBinary() = if (this == 'B' || this == 'R') '1' else '0'
private fun Char.toBInt() = toString().toInt()