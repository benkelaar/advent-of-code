package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.tail

private sealed interface SnailfishNumber {
    val nestCount: Int
    fun magnitude(): Long

    fun addReduce(snailfishNumber: SnailfishNumber): SnailfishNumber {
        var result: SnailfishNumber = SnailfishPair(nest(), snailfishNumber.nest(), 0)
        while (result.shouldExplode() || result.shouldSplit()) {
            result = if (result.shouldExplode()) result.explode().result else result.split()
        }
        return result
    }

    fun addLeft(addedValue: Int): SnailfishNumber
    fun addRight(addedValue: Int): SnailfishNumber

    fun split(): SnailfishNumber
    fun shouldSplit(): Boolean
    fun explode(): ExplodeResult
    fun shouldExplode(): Boolean

    fun nest(): SnailfishNumber

    companion object {
        fun parse(input: String, nestCount: Int = 0): SnailfishNumber = if (input.first() == '[') {
            val commaIndex = findCommaIndex(input)
            SnailfishPair(parse(input.substring(1, commaIndex), nestCount + 1), parse(input.substring(commaIndex + 1, input.lastIndex), nestCount + 1), nestCount)
        }
        else if (input.first().isDigit()) RegularSnailfish(input.first().digitToInt(), nestCount)
        else throw IllegalStateException(input)

        private fun findCommaIndex(input: String): Int {
            var nestCount = 0
            for (i in 1..input.lastIndex - 2) {
                when (input[i]) {
                    ',' -> if (nestCount == 0) return i
                    '[' -> nestCount++
                    ']' -> nestCount--
                }
            }
            throw IllegalStateException("No pair comma found")
        }
    }
}

private data class RegularSnailfish(val value: Int, override val nestCount: Int) : SnailfishNumber {
    override fun magnitude() = value.toLong()
    override fun addLeft(addedValue: Int) = copy(value = value + addedValue)
    override fun addRight(addedValue: Int) = addLeft(addedValue)

    override fun shouldSplit() = value > 9
    override fun split() = if (value < 10) this else SnailfishPair(
        RegularSnailfish(value/2, nestCount + 1),
        RegularSnailfish((value + 1)/2, nestCount + 1),
        nestCount
    )

    override fun shouldExplode() = false
    override fun explode() = Nosplosion(this)

    override fun nest() = copy(nestCount = nestCount + 1)

    override fun toString() = value.toString()
}

private data class SnailfishPair(val left: SnailfishNumber, val right: SnailfishNumber, override val nestCount: Int) : SnailfishNumber {
    override fun magnitude(): Long = 3 * left.magnitude() + 2 * right.magnitude()
    override fun addLeft(addedValue: Int) = copy(left = left.addLeft(addedValue))
    override fun addRight(addedValue: Int) = copy(right = right.addRight(addedValue))

    override fun split(): SnailfishPair {
        val leftSplit = left.split()
        return SnailfishPair(leftSplit, if (leftSplit == left) right.split() else right, nestCount)
    }

    override fun shouldSplit() = left.shouldSplit() || right.shouldSplit()

    override fun explode() =
        if (nestCount == 4) Explosion(RegularSnailfish(0, nestCount), (left as RegularSnailfish).value, (right as RegularSnailfish).value)
        else when (val el = left.explode()) {
            is Explosion -> Explosion(copy(left = el.result, right = right.addLeft(el.scrapnelRight)), el.scrapnelLeft, 0)
            is Nosplosion -> {
                when (val er = right.explode()) {
                    is Explosion -> Explosion(copy(left = left.addRight(er.scrapnelLeft), right = er.result), 0, er.scrapnelRight)
                    is Nosplosion -> Nosplosion(this)
                }
            }
        }

    override fun shouldExplode() = nestCount > 3 || left.shouldExplode() || right.shouldExplode()

    override fun nest() = SnailfishPair(left.nest(), right.nest(), nestCount + 1)

    override fun toString() = "[$left,$right]"
}

private sealed interface ExplodeResult { val result: SnailfishNumber }
private data class Explosion(override val result: SnailfishNumber, val scrapnelLeft: Int, val scrapnelRight: Int) : ExplodeResult
private data class Nosplosion(override val result: SnailfishNumber) : ExplodeResult

class SnailfishMath : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val snailfish = input.nonBlank().map { SnailfishNumber.parse(it) }
        val endPair = snailfish.tail().fold(snailfish.first(), SnailfishNumber::addReduce)

        val maxScore = snailfish.flatMap { a -> snailfish.flatMap { b -> listOf(a to b, b to a) } }
            .filter { (a, b) -> a != b }
            .maxOf { (a, b) -> a.addReduce(b).magnitude() }

        return endPair.magnitude() to maxScore
    }
}