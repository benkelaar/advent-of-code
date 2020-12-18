package com.bartenkelaar.year2020.math

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.year2020.math.Operation.PLUS
import com.bartenkelaar.year2020.math.Operation.TIMES

class ExpressionSolver : Solver {
    override fun solve(input: List<String>): Pair<Number, Number> {
        return input.totalSum { Expression(it) } to input.totalSum { AdvancedExpression(it) }
    }

    private fun List<String>.totalSum(transform: (String) -> SolvableExpression) =
        map(transform).map { it.solve().first }.sum()
}

interface SolvableExpression {
    fun solve(): Pair<Long, Int>
}

data class Expression(val string: String) : SolvableExpression {
    override fun solve(): Pair<Long, Int> {
        val chars = string.toCharStrings()
        var result = 0L

        var i = 0
        var operation = PLUS
        while (i < chars.size) {
            val c = chars[i++]
            when {
                c[0].isDigit() -> result = operation.op(result, c.toLong())
                c == "+" -> operation = PLUS
                c == "*" -> operation = TIMES
                c == "(" -> {
                    val (innerRes, consumed) = Expression(chars.slice(i..chars.lastIndex).joinToString("")).solve()
                    i += consumed
                    result = operation.op(result, innerRes)
                }
                c == ")" -> break
            }
        }
        return result to i
    }
}

data class AdvancedExpression(val string: String, val plusOnly: Boolean = false) : SolvableExpression {
    override fun solve(): Pair<Long, Int> {
        val chars = string.toCharStrings()
        var result = 0L

        var i = 0
        var operation = PLUS
        while (i < chars.size) {
            val c = chars[i++]

            fun subExpression(plusOnly: Boolean): Long {
                val rest = chars.slice(i..chars.lastIndex).joinToString("")
                val (innerRes, consumed) = AdvancedExpression(rest, plusOnly).solve()
                i += consumed
                return innerRes
            }

            when {
                c[0].isDigit() -> result = operation.op(result, c.toLong())
                c == "+" -> operation = PLUS
                c == "*" -> if (plusOnly) break else {
                    operation = TIMES
                    result = operation.op(result, subExpression(true))
                }
                c == "(" -> result = operation.op(result, subExpression(false))
                c == ")" -> break
            }
        }
        return result to if (plusOnly && i != chars.size) i - 1 else i
    }
}

enum class Operation(val op: (Long, Long) -> Long) { PLUS(Long::plus), TIMES(Long::times) }

private fun String.toCharStrings() = split("").nonBlank()
