package com.bartenkelaar.util

data class Grid<T>(val rows: List<List<T>>) {
    fun rowSize() = rows.first().size
    fun count(selector: (T) -> Boolean) = rows.sumOf { row -> row.count(selector) }

    fun <U> flattenedMap(mapper: (T) -> U) = rows.flatten().map(mapper)

    fun <U> mapCoordinated(mapper: (Coordinate, T) -> U) = Grid(rows.mapIndexed { y, row ->
            row.mapIndexed { x, value -> mapper(Coordinate(x, y), value) }
    })

    fun <U> mapWithNeighbours(mapper: (T, Grid<T>) -> U): Grid<U> = mapCoordinated { c, value ->
        mapper(value, Grid(rows.getNeighbours(c.y).map { it.getNeighbours(c.x) }))
    }

    fun <U> mapWithNeighboursOrDefault(default: T, mapper: (T, Grid<T>) -> U) = mapCoordinated { c, value ->
        mapper(value, Grid(rows.getNeighboursOrDefault(c.y) { (c.x + 1) of default }.map {
            it.getNeighboursOrDefault(c.x) { default }
        }))
    }

    fun with(vararg values: Pair<Coordinate, T>) = with(values.toMap())
    fun with(values: Map<Coordinate, T>) = mapCoordinated { c, v -> values[c] ?: v }

    private fun <V> List<V>.getNeighbours(index: Int) =
        listOfNotNull(getOrNull(index - 1), get(index), getOrNull(index + 1))

    private fun <V> List<V>.getNeighboursOrDefault(index: Int, default: (Int) -> V) =
        listOf(getOrElse(index - 1, default), this[index], getOrElse(index + 1, default))

    companion object {
        fun forChars(lines: List<String>) = Grid(lines.nonBlank().map { it.toList() })

        fun forBits(input: List<String>, trueValue: Char) =
            Grid(input.nonBlank().map { row -> row.toList().map { Bit(it, trueValue) } })
    }
}