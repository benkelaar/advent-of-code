package com.bartenkelaar.year2015.organization

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

private data class Person(val name: String, val opinions: Map<String, Int>) {
    fun happiness(left: Person, right: Person) = opinions[left.name]!! + opinions[right.name]!!

    fun withPreference(neighbour: String, happinessGain: Int) = copy(opinions = opinions + (neighbour to happinessGain))
}

private data class Preference(val person: String, val neighbour: String, val happinessGain: Int) {
    fun toPair() = neighbour to happinessGain

    companion object {
        private val REGEX = """([A-Z][a-z]+) would (gain|lose) (\d+) happiness units by sitting next to ([A-Z][a-z]+)\.""".toRegex()

        fun fromLine(line: String): Preference {
            val (person, direction, gain, neighbour) = REGEX.matchEntire(line)!!.destructured
            val multiplier = if (direction == "lose") -1 else 1
            return Preference(person, neighbour, multiplier * gain.toInt())
        }
    }
}

class SeatingArranger : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val preferences = input.nonBlank().map { Preference.fromLine(it) }
        val people = preferences.groupBy { it.person }
            .map { (person, preferences) -> Person(person, preferences.associate { it.toPair() }) }
        val allPeople = people.map { it.withPreference("Bart", 0) } +
                Person("Bart", people.associate { it.name to 0 })
        return people.findMaximumHappiness() to allPeople.findMaximumHappiness()
    }

    private fun List<Person>.findMaximumHappiness() = allArrangements(map { listOf(it) }).maxOf { it.sumHappiness() }

    private fun List<Person>.sumHappiness() = mapIndexed { i, person ->
        person.happiness(if (i == 0) last() else this[i - 1], this[(i + 1) % size])
    }.sum()

    private tailrec fun List<Person>.allArrangements(arrangements: List<List<Person>> = emptyList()): List<List<Person>> =
        if (arrangements.first().size == size) arrangements
        else allArrangements(arrangements.flatMap { seated -> (this - seated.toSet()).map { seated + it } })
}

