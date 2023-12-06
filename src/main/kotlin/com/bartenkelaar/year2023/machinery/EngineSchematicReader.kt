package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.*

private sealed interface SchematicEntity

private data class EnginePart(
    val spot: Coordinate,
    val symbol: Char
) : SchematicEntity

private data class SchematicNumber (
    val range: IntRange,
    val value: Int,
) : SchematicEntity

private data class PartNumber (
    val part: EnginePart,
    val number: SchematicNumber
) {
    fun value() = number.value

    companion object {
        fun fromEntity(entity: SchematicEntity, y: Int, parts: List<List<EnginePart>>): PartNumber? {
            if (entity !is SchematicNumber) return null
            val xRange = entity.range.grow()
            val rows = parts.boundedSlice(y - 1, y + 1)
            val part = rows.firstNotNullOfOrNull { it.find { part -> part.spot.x in xRange } }
            return part?.let { PartNumber(it, entity) }
        }
    }
}

class EngineSchematicReader : Solver() {
    private val regex = "(\\d+)|([^.0-9])".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val entities = input.mapIndexed { y, row -> regex.findAll(row).map { read(it, y) }.toList() }
        val parts = entities.map { row -> row.mapNotNull { if (it is EnginePart) it else null } }
        val partNumbers = entities.flatMapIndexed { y, es -> es.mapNotNull { PartNumber.fromEntity(it, y, parts) } }
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
        return if (value[0].isDigit()) SchematicNumber(xs, value.toInt())
        else EnginePart(Coordinate(xs.first, y), value[0])
    }
}