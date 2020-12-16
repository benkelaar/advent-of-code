package com.bartenkelaar.year2020.gaming

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.year2020.gaming.Operation.*

class GameRunner : Solver {
    override fun solve(input: List<String>): Pair<Int, Number> {
        val instructions = input.nonBlank().map { Instruction.forLine(it.trim()) }
        val (result, _) = runProgram(instructions)
        val (loopValue, visited) = result
        return loopValue to findFixedValue(instructions, visited)
    }

    private fun findFixedValue(instructions: List<Instruction>, visited: List<Int>): Int {
        val changeAbleIndices = visited.filterNot { instructions[it].operation == ACCUMULATE }
        changeAbleIndices.reversed().forEach {
            val mutableInstructions = instructions.toMutableList()
            mutableInstructions[it] = mutableInstructions[it].switch()
            val (value, terminated) = runProgram(mutableInstructions.toList())
            if (terminated) return value.first
        }
        throw IllegalStateException()
    }

    private fun runProgram(instructions: List<Instruction>): Pair<Pair<Int, List<Int>>, Boolean> {
        var accumulator = 0
        var index = 0
        val visitedInstructions = mutableListOf<Int>()
        while (true) {
            if (index >= instructions.size) return accumulator to visitedInstructions.toList() to true
            with(instructions[index]) {
                if (index in visitedInstructions) return accumulator to visitedInstructions.toList() to false

                visitedInstructions += index

                if (operation == ACCUMULATE) accumulator += amount
                else if (operation == JUMP) index += amount

                if (operation != JUMP) index++
            }
        }
    }
}

data class Instruction(val operation: Operation, val amount: Int) {
    fun switch() = Instruction(if (operation == JUMP) NOPE else JUMP, amount)

    companion object {
        fun forLine(line: String): Instruction {
            val (operationName, amountString) = line.split(" ")
            val sign = if (amountString[0] == '+') 1 else -1
            val amount = amountString.slice(1..amountString.lastIndex).toInt() * sign
            return Instruction(Operation.forName(operationName), amount)
        }
    }
}

enum class Operation {
    ACCUMULATE, JUMP, NOPE;

    companion object {
        fun forName(name: String) = when (name) {
            "acc" -> ACCUMULATE
            "jmp" -> JUMP
            "nop" -> NOPE
            else -> throw IllegalArgumentException("Unknown operation: $name")
        }
    }
}