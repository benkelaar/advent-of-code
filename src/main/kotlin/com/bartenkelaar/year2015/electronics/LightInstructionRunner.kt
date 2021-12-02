package com.bartenkelaar.year2015.electronics

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.year2015.electronics.LightAction.*

@JvmRecord
data class LightCoordinate(val x: Int, val y: Int)

data class Instruction(val action: LightAction, val bottomLeft: LightCoordinate, val topRight: LightCoordinate) {
    fun applyTo(lights: Array<BooleanArray>, dimmableLights: Array<IntArray>) {
        (bottomLeft.x .. topRight.x).forEach { x ->
            (bottomLeft.y .. topRight.y).forEach { y ->
                when(action) {
                    ON -> {
                        lights[x][y] = true
                        dimmableLights[x][y] += 1
                    }
                    TOGGLE -> {
                        lights[x][y] = !lights[x][y]
                        dimmableLights[x][y] += 2
                    }
                    OFF -> {
                        lights[x][y] = false
                        if (dimmableLights[x][y] > 0) dimmableLights[x][y] -= 1
                    }
                }
            }
        }
    }

    companion object {
        private val INSTRUCTION_REGEX = """(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)""".toRegex()

        fun parseFrom(line: String): Instruction {
            val (action, x1, y1, x2, y2) = INSTRUCTION_REGEX.find(line)?.destructured!!
            return Instruction(
                action = LightAction.parseFrom(action),
                bottomLeft = LightCoordinate(x1.toInt(), y1.toInt()),
                topRight = LightCoordinate(x2.toInt(), y2.toInt())
            )
        }
    }
}

enum class LightAction {
    ON, TOGGLE, OFF;

    companion object {
        fun parseFrom(action: String) = valueOf(action.split(" ").last().uppercase())
    }
}

class LightInstructionRunner : Solver() {
    private val lights = arrayOf(*(0..999).map { booleanArrayOf(*(0..999).map { false }.toBooleanArray()) }.toTypedArray())
    private val dimmableLights = arrayOf(*(0..999).map { intArrayOf(*(0..999).map { 0 }.toIntArray()) }.toTypedArray())

    override fun solve(input: List<String>): Pair<Number, Any> {
        val instructions = input.nonBlank().map { Instruction.parseFrom(it) }
        instructions.forEach { it.applyTo(lights, dimmableLights) }
        return lights.sumOf { row -> row.count { it } } to dimmableLights.sumOf { it.sum() }
    }
}