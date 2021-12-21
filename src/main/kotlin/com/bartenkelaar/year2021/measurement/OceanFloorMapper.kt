package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.*

@JvmInline
private value class OceanFloorImage(val grid: Grid<Char>) {
    fun expand(c: Char) = OceanFloorImage(Grid(mutableListOf<List<Char>>().apply {
        add(emptyRow(c))
        addAll(growColumns(c))
        add(emptyRow(c))
    }))

    fun countLit() = grid.count { it == '#' }

    private fun emptyRow(c: Char) = (grid.rowSize() + 2).of(c)
    private fun growColumns(c: Char) = grid.rows.map { listOf(c) + it + c }
}

@JvmInline
private value class Decoder(val keyString: String) {
    fun decode(oceanFloorImage: OceanFloorImage, default: Char) = OceanFloorImage(
        oceanFloorImage.grid.mapWithNeighboursOrDefault(default) { keyString[it.toDecodeIndex()] }
    )

    private fun Grid<Char>.toDecodeIndex() =
        flattenedMap { if (it == '#') 1 else 0 }
            .joinToString("")
            .toInt(2)
}

class OceanFloorMapper : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val decoder = Decoder(input.first())
        val startingGrid = OceanFloorImage(Grid.forChars(input.tail()))

        var secondCount = 0
        var grid = startingGrid
        for (i in 0 until 50) {
            if (i == 2) secondCount = grid.countLit()
            val infiniteChar = if (i % 2 == 0) '.' else decoder.keyString.first()
            grid = grid.expand(infiniteChar)
            grid = decoder.decode(grid, infiniteChar)
        }

        return secondCount to grid.countLit()
    }
}
