package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

private data class Cave(val name: String, val neighbours: Set<String>) {
    val large = name.uppercase() == name
    val small get() = !large
}

private data class Path(val path: List<Cave>) {
    val current get() = path.last()

    fun reachedEnd() = path.last().name == "end"

    fun withOrNull(c: Cave, maxDoubles: Int) = Path(path + c).takeIf { it.isValid(maxDoubles) }

    private fun isValid(maxDoubles: Int) = path.count { it.small } - path.toSet().count { it.small } <= maxDoubles

    companion object {
        fun of(cave: Cave) = Path(listOf(cave))
    }
}

class PathNavigator : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val connections = input.nonBlank().map { it.split("-") }
        val caves = connections
            .flatMap { (a, b) -> setOf(a to b, b to a) }
            .groupBy { it.first }
            .map { (cave, paths) -> Cave(cave, paths.map { it.second }.toSet() - "start") }
            .associateBy { it.name }

        val startPaths = setOf(Path.of(caves.getValue("start")))
        val paths = caves.findPaths(startPaths, 0)
        val morePaths = caves.findPaths(startPaths, 1)
        return paths.size to morePaths.size
    }

    private tailrec fun Map<String, Cave>.findPaths(paths: Set<Path>, maxDoubles: Int): Set<Path> =
        if (paths.all { it.reachedEnd() }) paths
        else findPaths(paths.flatMap { path ->
            if (path.reachedEnd()) setOf(path)
            else path.current.neighbours.mapNotNull { path.withOrNull(getValue(it), maxDoubles) }
        }.toSet(), maxDoubles)
}
