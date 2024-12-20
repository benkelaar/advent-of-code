package com.bartenkelaar.util

data class Grid<T>(val rows: List<List<T>>) {
    fun rowSize() = rows.first().size

    fun count(selector: (T) -> Boolean) = rows.sumOf { row -> row.count(selector) }

    fun countCoordinated(selector: (Coordinate, T) -> Boolean) = sumOfCoordinated { c, x -> if (selector(c, x)) 1 else 0 }

    fun sumOfCoordinated(toInt: (Coordinate, T) -> Int) = mapCoordinated(toInt).sum()

    fun <U> flattenedMap(mapper: (T) -> U) = rows.flatten().map(mapper)

    fun <U> map(mapper: (T) -> U) = Grid(rows.map { row -> row.map { value -> mapper(value) } })

    fun <U> mapCoordinated(mapper: (Coordinate, T) -> U) =
        Grid(
            rows.mapIndexed { y, row ->
                row.mapIndexed { x, value -> mapper(Coordinate(x, y), value) }
            },
        )

    fun <U> mapWithNeighbours(mapper: (T, Grid<T>) -> U): Grid<U> =
        mapCoordinated { c, value ->
            mapper(value, Grid(rows.getNeighbours(c.y).map { it.getNeighbours(c.x) }))
        }

    fun <U> mapWithNeighboursOrDefault(default: T, mapper: (T, Grid<T>) -> U) =
        mapCoordinated { c, value ->
            mapper(
                value,
                Grid(
                    rows.getNeighboursOrDefault(c.y) { (c.x + 1) of default }.map {
                        it.getNeighboursOrDefault(c.x) { default }
                    },
                ),
            )
        }

    operator fun get(c: Coordinate) = rows[c.y][c.x]

    fun getOrNull(c: Coordinate) = rows.getOrNull(c.y)?.getOrNull(c.x)

    fun with(vararg values: Pair<Coordinate, T>) = with(values.toMap())

    fun with(values: Map<Coordinate, T>) = mapCoordinated { c, v -> values[c] ?: v }

    private fun <V> List<V>.getNeighbours(index: Int) = listOfNotNull(getOrNull(index - 1), get(index), getOrNull(index + 1))

    private fun <V> List<V>.getNeighboursOrDefault(index: Int, default: (Int) -> V) =
        listOf(getOrElse(index - 1, default), this[index], getOrElse(index + 1, default))

    fun anyCoordinated(condition: (Coordinate, T) -> Boolean): Boolean {
        for (y in rows.indices) {
            for (x in rows[y].indices) {
                if (condition(Coordinate(x, y), rows[y][x])) return true
            }
        }
        return false
    }

    fun coordinateOfFirst(t: T): Coordinate {
        val y = rows.indexOfFirst { t in it }
        val x = rows[y].indexOf(t)
        return Coordinate(x, y)
    }

    operator fun contains(c: Coordinate) = c.x >= 0 && c.x < rowSize() && c.y >= 0 && c.y < rows.size

    fun print(toString: (T) -> String = { "$it" }) = rows.forEach { println(it.joinToString("", transform = toString)) }

    fun toCoordinateMap() = mapCoordinated { c, t -> c to t }.rows.flatten().toMap()

    fun coordinates(): Set<Coordinate> = rows.flatMapIndexed { y, row -> row.indices.map { x -> Coordinate(x, y) } }.toSet()

    companion object {
        fun forChars(lines: List<String>) = Grid(lines.nonBlank().map { it.toList() })

        fun forBits(input: List<String>, trueValue: Char) = Grid(input.nonBlank().map { row -> row.toList().map { Bit(it, trueValue) } })
    }
}

fun Grid<Int>.sum() = rows.sumOf { it.sum() }

fun Grid<Long>.max() = rows.maxOf { it.maxOrNull()!! }
