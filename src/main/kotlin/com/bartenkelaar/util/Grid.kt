package com.bartenkelaar.util

data class Grid<T>(val rows: List<List<T>>) {
    fun rowSize() = rows.first().size
    fun count(selector: (T) -> Boolean) = rows.sumOf { row -> row.count(selector) }

    fun <U> flattenedMap(mapper: (T) -> U) = rows.flatten().map(mapper)

    fun <U> mapWithNeighboursOrDefault(default: T, mapper: (Grid<T>) -> U) = Grid(rows.mapIndexed { y, row ->
            row.indices.map { x ->
                mapper(Grid(rows.getNeighboursOrDefault(y) { (x + 1) of default }.map {
                    it.getNeighboursOrDefault(x) { default }
                }))
            }
        })

    private fun <V> List<V>.getNeighboursOrDefault(index: Int, default: (Int) -> V) =
        listOf(getOrElse(index - 1, default), this[index], getOrElse(index + 1, default))

    companion object {
        fun forChars(lines: List<String>) = Grid(lines.nonBlank().map { it.toList() })
    }
}