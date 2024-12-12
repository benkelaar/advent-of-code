package com.bartenkelaar.year2023.machinery

import com.bartenkelaar.util.Solver

sealed interface Operation

class Remove : Operation

data class Set(val focalStrength: Int) : Operation

data class Instruction(val label: String, val op: Operation) {
    fun applyTo(boxes: MutableMap<Long, List<Pair<String, Int>>>) {
        val box = label.toHASH()
        val list = boxes[box] ?: emptyList()
        when (op) {
            is Remove -> boxes[box] = list.filter { it.first != label }
            is Set -> {
                val hasLens = list.any { it.first == label }
                val targetLens = label to op.focalStrength
                boxes[box] =
                    if (hasLens) list.map { if (it.first == label) targetLens else it }
                    else list + targetLens
            }
        }
    }

    companion object {
        fun forString(string: String): Instruction {
            val (label, s) = string.split("[-=]".toRegex())
            return Instruction(label, if (s.isEmpty()) Remove() else Set(s.toInt()))
        }
    }
}

class LensProductionInitializer : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val instructionStrings = input.first().split(",")
        val instructions = instructionStrings.map(Instruction::forString)

        val boxes = mutableMapOf<Long, List<Pair<String, Int>>>()
        instructions.forEach { it.applyTo(boxes) }
        val totalFocussingPower = boxes.entries.sumOf { (bi, lenses) ->
            lenses.mapIndexed { i, (_, p) -> (bi + 1) * (i + 1) * p }.sum()
        }

        return instructionStrings.sumOf { it.toHASH() } to totalFocussingPower
    }
}

private fun String.toHASH(): Long {
    var value = 0L
    forEach {
        val code = it.code
        value += code
        value *= 17
        value %= 256
    }
    return value
}
