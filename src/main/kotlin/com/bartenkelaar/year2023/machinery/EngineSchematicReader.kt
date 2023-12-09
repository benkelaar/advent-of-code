package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.*

private sealed interface SchematicEntity {
    val coordinate: Coordinate
}

private data class EnginePart(
    override val coordinate: Coordinate,
    val symbol: Char
) : SchematicEntity

private data class SchematicNumber (
    override val coordinate: Coordinate,
    val xRange: IntRange,
    val value: Int,
) : SchematicEntity

private data class PartNumber (
    val part: EnginePart,
    val number: SchematicNumber
) {
    fun value() = number.value

    companion object {
        fun fromEntity(entity: SchematicEntity, parts: List<List<EnginePart>>): PartNumber? {
            if (entity !is SchematicNumber) return null
            val xRange = entity.xRange.grow()
            val y = entity.coordinate.y
            val rows = parts.boundedSlice(y - 1, y + 1)
            val part = rows.firstNotNullOfOrNull { it.find { part -> part.coordinate.x in xRange } }
            return part?.let { PartNumber(it, entity) }
        }
    }
}

class EngineSchematicReader : Solver() {
    private val regex = """(\d+)|([^.\d])""".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val entities = input.mapIndexed { y, row -> regex.findAll(row).map { read(it, y) }.toList() }
        val parts = entities.map { row -> row.mapNotNull { if (it is EnginePart) it else null } }
        val partNumbers = entities.flatMap { es -> es.mapNotNull { PartNumber.fromEntity(it, parts) } }
        val numbersByPart = partNumbers.groupBy { it.part }

        val gears = parts.flatMap { row -> row.filter {
                part -> part.symbol == '*' && numbersByPart.getValue(part).size == 2 }
        }
        return partNumbers.sumOf { it.value() } to
                gears.sumOf { gear -> numbersByPart.getValue(gear).productOf { it.value() } }
    }

    private fun read(result: MatchResult, y: Int): SchematicEntity {
        val xs = result.range
        val value = result.value
        val coordinate = Coordinate(xs.first, y)
        return if (value.first().isDigit()) SchematicNumber(coordinate, xs, value.toInt())
        else EnginePart(coordinate, value[0])
    }
}