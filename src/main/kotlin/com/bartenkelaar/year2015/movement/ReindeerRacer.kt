package com.bartenkelaar.year2015.movement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import java.lang.Integer.min

data class ReindeerSpecs(val name: String, val speed: Int, val flightTime: Int, val restTime: Int) {
    fun travelledIn(travelTimeSeconds: Int): Int {
        val cycleTime = flightTime + restTime
        val cycles = travelTimeSeconds / cycleTime
        val remainder = travelTimeSeconds - cycles * cycleTime
        return (cycles * flightTime + min(flightTime, remainder)) * speed
    }

    companion object {
        private val REGEX = """(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.""".toRegex()

        fun forLine(line: String): ReindeerSpecs {
            val (name, speed, flightTime, restTime) = REGEX.matchEntire(line)!!.destructured
            return ReindeerSpecs(name, speed.toInt(), flightTime.toInt(), restTime.toInt())
        }
    }
}

class ReindeerRacer : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val specs = input.nonBlank().map { ReindeerSpecs.forLine(it) }
        val distanceTravelled = specs.map { it.travelledIn(2503) }

        val pointsGained = (1..2503)
            .map { time -> specs.maxByOrNull { it.travelledIn(time) }!!.name }
            .groupBy { it }
            .maxOf { entry -> entry.value.size}

        return distanceTravelled.maxOrNull()!! to pointsGained
    }
}
