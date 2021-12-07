package com.bartenkelaar.year2015.delivery

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

data class Path(val cities: List<City>) {
    val distance get() = cities.zipWithNext { from, to -> from.neighbours[to.name]!! }.sum()

    operator fun plus(city: City) = Path(cities + city)
}

data class City(val name: String, val neighbours: Map<String, Int>)
data class Distance(val from: String, val to: String, val distance: Int)

class RoutePlanner : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val distances = input.nonBlank()
            .map { it.split(" to ", " = ") }
            .map { (f, t, d) -> Distance(f, t, d.toInt()) }
        val cities = distances
            .flatMap { listOf(it.from, it.to) }
            .toSet()
            .map { name -> City(name, distances.distancesFor(name)) }
        val paths = cities.allPaths(cities.map { Path(listOf(it)) })

        return paths.minByOrNull { it.distance }!!.distance to paths.maxByOrNull { it.distance }!!.distance
    }

    private tailrec fun List<City>.allPaths(paths: List<Path>): List<Path> = if (paths.first().cities.size == size) paths else
        allPaths(paths.flatMap { p -> (this - p.cities.toSet()).map { city -> p + city } })

    private fun List<Distance>.distancesFor(city: String) =
        filter { it.from == city }.associate { it.to to it.distance } +
                filter { it.to == city }.associate { it.from to it.distance }
}
