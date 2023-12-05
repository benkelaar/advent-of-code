package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.boundedSlice
import com.bartenkelaar.util.grow

private sealed interface SchematicEntity

private data class EnginePart(
    val x: Int,
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
    companion object {
        fun fromEntity(entity: SchematicEntity, y: Int, parts: List<List<EnginePart>>): PartNumber? {
            if (entity !is SchematicNumber) return null
            val xRange = entity.range.grow()
            val rows = parts.boundedSlice(y - 1, y + 1)
            val part = rows.mapNotNull { it.find { part -> part.x in xRange } }.find { true }
            return if (part != null) PartNumber(part, entity) else null
        }
    }
}

class EngineSchematicReader : Solver() {
    private val regex = "(\\d+)|([^.0-9])".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val entities = input.map { row -> regex.findAll(row).map { read(it) }.toList() }
        val parts = entities.map { row -> row.mapNotNull { if (it is EnginePart) it else null } }
        val partNumbers = entities.flatMapIndexed { y, es -> es.mapNotNull { PartNumber.fromEntity(it, y, parts) } }
        return partNumbers.sumOf { it.number.value } to 0
    }

    private fun read(result: MatchResult): SchematicEntity {
        val xs = result.range
        val value = result.value
        return if (value[0].isDigit()) SchematicNumber(xs, value.toInt()) else EnginePart(xs.first, value[0])
    }
}