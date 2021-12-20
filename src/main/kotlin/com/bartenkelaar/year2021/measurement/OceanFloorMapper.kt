package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.of
import com.bartenkelaar.util.tail

@JvmInline
private value class ImageGrid(val grid: List<List<Char>>) {
    fun expand(c: Char) = ImageGrid(mutableListOf<List<Char>>().apply {
        add(emptyRow(c))
        addAll(growColumns(c))
        add(emptyRow(c))
    })
    fun countLit() = grid.sumOf { row -> row.count { it == '#' } }

    private fun emptyRow(c: Char) = (grid.first().size + 2).of(c)
    private fun growColumns(c: Char) = grid.map { listOf(c) + it + c }
}

@JvmInline
private value class Decoder(val keyString: String) {
    fun decode(imageGrid: ImageGrid, default: Char) = ImageGrid(
        imageGrid.grid.mapIndexed { y, row ->
            row.mapIndexed { x, _ -> keyString[getNumber(imageGrid.grid, x, y, default)] }
        }
    )

    private fun getNumber(grid: List<List<Char>>, x: Int, y: Int, default: Char): Int {
        fun List<List<Char>>.getRow(rowIndex: Int) = getOrElse(rowIndex) { (x + 2).of(default) }
        fun List<Char>.getChars() = listOf(getOrElse(x - 1) { default }, get(x), getOrElse(x + 1)  { default })

        return (grid.getRow(y - 1).getChars() + grid[y].getChars() + grid.getRow(y + 1).getChars())
            .map { if (it == '#') 1 else 0 }
            .joinToString("")
            .toInt(2)
    }
}

class OceanFloorMapper : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val decoder = Decoder(input.first())
        val startingGrid = ImageGrid(input.tail().nonBlank().map { it.toList() })

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
