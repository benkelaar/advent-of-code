package com.bartenkelaar.year2021.measurement

import com.bartenkelaar.util.Solver
import java.lang.Integer.min

/**
 * target area: x=32..65, y=-225..-177
 *
 * Part 1:
 * Vy = Vyi - t
 * Sy = Vyi*(t+1) - (t(t+1))/2
 *
 * Vyi = (Sy + (t(t+1))/2)/(t+1)
 * tmin for x=32..65 == 8
 * Ymax will happen when Vyi == t (saddle point will be where Vy = 0)
 * and will be (Vyi(Vyi+1))/2 (Follows from pluging into equation for Sy)
 *
 * So the higher Vyi is the better, but you have to prevent overshoot
 * Given parabola with constant decrease (and there is a Vx that comes to a stop within the range),
 * when returning to y = 0, Vy0 == -Vyi
 *
 * Lowest that you can get Vy0 without overshoot in order to reach the area = -224 (since next step speed will be one lower)
 *
 * Part 2:
 * xRange = 8..65
 * yRange = -225..224
 */
class ProbeLauncher : Solver() {
    private val regex = """target area: x=(\d+)..(\d+), y=(-\d+)..(-\d+)""".toRegex()

    override fun solve(input: List<String>): Pair<Any, Any> {
        val (x1, x2, y1, y2) = regex.matchEntire(input.first())!!.destructured
        val xTarget = x1.toInt()..x2.toInt()
        val yTarget = y1.toInt()..y2.toInt()

        val vyMax = -yTarget.first - 1
        val yMax = vyMax*(vyMax + 1)/2

        val possibleAngles = (0..xTarget.last).flatMap { vx -> (yTarget.first..vyMax).map { vy -> vx to vy } }
            .filter { (vx, vy) -> landsIn(vx, vy, xTarget, yTarget) }
        return yMax to possibleAngles.count()
    }

    private fun landsIn(vx: Int, vy: Int, xTarget: IntRange, yTarget: IntRange): Boolean {
        var t = 0
        do {
            val xFactor = min(vx, t)
            val px = vx*(xFactor+1) - xFactor*(xFactor+1)/2
            val py = vy*(t+1) - t*(t+1)/2
            t++
            if (px in xTarget && py in yTarget) return true
        } while (px < xTarget.last && py > yTarget.first)
        return false
    }
}
