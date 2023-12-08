package com.bartenkelaar.year2023.gaming

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.productOf
import com.bartenkelaar.util.tail
import kotlin.math.floor
import kotlin.math.sqrt

/*
    x(t-x) > r
    -x^2 + xt - r > 0
    quadratic with a = -1, b = t, c = -r
*/
class BoatRacer : Solver() {
    private val whitespaceRegex = "\\s+".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val times = input.first.readNumbers()
        val records = input[1].readNumbers()

        val xs = times.zip(records).map { (t, r) -> calculateX(t, r) }
        val nextXs = xs.map { it.nextInt() }
        val waysProduct = nextXs.zip(times).productOf { (x, t) -> howManyWays(x, t) }

        val megaTime = times.joinToString("").toLong()
        val megaRecord = records.joinToString("").toLong()
        val megaX = calculateX(megaTime, megaRecord)
        val megaWays = howManyWays(megaX.nextInt(), megaTime)
        return waysProduct to megaWays
    }

    private fun Double.nextInt() = floor(this + 1).toInt()

    private fun calculateX(t: Long, r: Long) = (t - sqrt(t * t - 4.0 * r)) / 2

    private fun howManyWays(x: Int, t: Long) = (t - 2 * x + 1).toInt()

    private fun String.readNumbers() = split(whitespaceRegex).tail().map { it.toLong() }
}