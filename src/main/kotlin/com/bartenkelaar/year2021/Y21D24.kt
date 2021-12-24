package com.bartenkelaar.year2021

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.partitionPer
import com.bartenkelaar.util.tail

private data class Instruction(val type: String, val params: List<String>) {
    fun intParam() = params.last().toInt()
}

private data class Program(val xOffset: Int, val yOffset: Int, val zDivisor: Int) {
    fun run(input: Int, z: Long) =
        if (inputMatches(input, z)) z / zDivisor else 26*(z/zDivisor) + input + yOffset

    fun inputMatches(input: Int, z: Long) = (z.mod(26) + xOffset) == input
}

/**
 *  Group program
 *  (i, z) -> if ((z % 26 + offset5) != i) {
 *    z = 26*(z/div4)+i+offset15
 *  }
 */
class Y21D24 : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val instructions = input.nonBlank()
            .map { it.split(" ") }
            .map { Instruction(it.first(), it.tail()) }

        val groupedInstructions = instructions.partitionPer { it.type == "inp" }
        val programs = groupedInstructions.map { group -> Program(group[5].intParam(), group[15].intParam(), group[4].intParam()) }

        var states = mapOf(0L to 0L)
        for (program in programs) {
            val inputs = 9 downTo 1
            states = states.flatMap { (oldInput, z) ->
                    inputs.map { i -> (oldInput * 10 + i to program.run(i, z))
                            .takeIf { program.zDivisor == 1 || program.inputMatches(i, z) }
                    }
                }.filterNotNull().toMap()
        }

        return states.keys.maxOrNull()!! to states.keys.minOrNull()!!
    }
}