package com.bartenkelaar.year2020.comunication

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.zipPerEmpty
import kotlin.math.sqrt

class ImageRebuilder(private val sample: Boolean = false) : Solver() {
    override fun solve(input: List<String>): Pair<Number, Number> {
        val tiles = input.zipPerEmpty()
            .filter { it.isNotEmpty() }
            .map { it.first().slice(5 until it.first().lastIndex).toInt() to it.slice(1..it.lastIndex) }
            .map { (id, lines) -> Tile.forLines(id, lines) }
            .toSet()

        val cornerTiles = tiles.filter { tile ->
            val otherEdges = (tiles - tile).flatMap { other -> other.matchEdges }
            tile.edges.count { edge -> edge !in otherEdges } == 2
        }.toSet()

        val image = reconstructImage(cornerTiles, tiles - cornerTiles)
        val totalPeaks = image.map { line -> line.count { c -> c == '#' } }.sum()

        val monsterCount = if (sample)
            image.mapIndexed { i, _ -> image.map { it[i] }.joinToString("") } .countMonsters()
        else image.countMonsters()

        return cornerTiles.map { it.id.toLong() }.reduce(Long::times) to totalPeaks - monsterCount * 15
    }

    private fun reconstructImage(cornerTiles: Set<Tile>, otherTiles: Set<Tile>) =
        arrangeTiles(cornerTiles, otherTiles).flatMap { it.toJoinedDataRows() }

    private fun arrangeTiles(cornerTiles: Set<Tile>, otherTiles: Set<Tile>): List<List<Tile>> {
        val imageCompositionCount = sqrt((4 + otherTiles.size).toDouble()).toInt()
        val unusedCorners = mutableSetOf(*cornerTiles.toTypedArray())
        val unusedTiles = mutableSetOf(*otherTiles.toTypedArray())

        val topRow = mutableListOf(unusedCorners.pop().toTopLeft(unusedTiles))
            .fillFrom(unusedTiles, imageCompositionCount - 2)
            .fillFrom(unusedCorners, 1)

        val composition = mutableListOf(topRow.toList())
        while (composition.size < imageCompositionCount - 1) {
            val firstTopEdge = composition.last().first().bottomEdge
            val firstTile = unusedTiles.popMatching(firstTopEdge).toMatchingTop(firstTopEdge)
            val row = mutableListOf(firstTile).fillFrom(unusedTiles, imageCompositionCount - 1)
            composition.add(row)
        }

        val bottomCornerTopEdge = composition.last().first().bottomEdge
        val bottomLeftCornerTile = unusedCorners.popMatching(bottomCornerTopEdge).toMatchingTop(bottomCornerTopEdge)
        val bottomRow = mutableListOf(bottomLeftCornerTile).fillFrom(unusedTiles, imageCompositionCount - 2)
        val bottomRightCorner = unusedCorners.single().toMatchingLeft(bottomRow.last().rightEdge)
        composition.add(bottomRow + bottomRightCorner)

        return composition.toList()
    }

    private fun List<Tile>.toJoinedDataRows(): List<String> {
        val data = map { it.toImageData() }
        return data.first().mapIndexed { i, _ -> data.joinToString("") { it[i] } }
    }

    private fun MutableList<Tile>.fillFrom(source: MutableSet<Tile>, amount: Int): MutableList<Tile> {
        for (i in 0 until amount) {
            val leftEdge = last().rightEdge
            add(source.popMatching(leftEdge).toMatchingLeft(leftEdge))
        }
        return this
    }

    private fun MutableSet<Tile>.popMatching(edge: String) = pop(find { edge in it.matchEdges }!!)

    private fun <T> MutableSet<T>.pop() = pop(first())

    private fun <T> MutableSet<T>.pop(value: T): T {
        remove(value)
        return value
    }
}

// Edges in order Up, down, left, right
data class Tile(val id: Int, val edges: List<String>, val lines: List<String>) {
    val matchEdges = edges + edges.map { it.reversed() }.toSet()
    val rightEdge = edges.last()
    val bottomEdge = edges[1]

    fun toTopLeft(otherTiles: Set<Tile>): Tile {
        var tile = this
        val otherEdges = otherTiles.flatMap { it.matchEdges }.toSet()
        if (edges[1] !in otherEdges) tile = tile.flipVertical()
        if (edges.last() !in otherEdges) tile = tile.flipHorizontal()
        return tile
    }

    fun toMatchingLeft(leftEdge: String): Tile {
        if (leftEdge == edges[2]) return this
        if (leftEdge == edges[2].reversed()) return flipVertical()
        if (leftEdge.matches(edges.last())) return flipHorizontal().toMatchingLeft(leftEdge)
        if (leftEdge.matches(edges.first()) || leftEdge.matches(edges[1])) return flip45Degrees().toMatchingLeft(leftEdge)
        throw IllegalStateException("Couldn't match edge $leftEdge")
    }

    fun toMatchingTop(topEdge: String): Tile {
        if (topEdge == edges.first()) return this
        if (topEdge == edges.first().reversed()) return flipHorizontal()
        if (topEdge.matches(edges[1])) return flipVertical().toMatchingTop(topEdge)
        if (topEdge.matches(edges[2]) || topEdge.matches(edges[3])) return flip45Degrees().toMatchingTop(topEdge)
        throw IllegalStateException("Couldn't match edge $topEdge")
    }

    fun toImageData() = lines.middle().map { it.middle() }

    private fun String.matches(other: String) = this == other || this == other.reversed()
    private fun flipVertical() = forLines(id, lines.reversed())
    private fun flipHorizontal() = forLines(id, lines.map { it.reversed() })
    private fun flip45Degrees() = forLines(id, lines.mapIndexed { i, _ -> lines.map { it[i] }.joinToString("") })

    companion object {
        fun forLines(id: Int, lines: List<String>) = Tile(
            id, listOf(
                lines.first(),
                lines.last(),
                lines.map { it.first() }.joinToString(""),
                lines.map { it.last() }.joinToString("")
            ), lines
        )
    }
}

fun List<String>.countMonsters(): Int {
    val headRegex = ".{18}#.".toRegex()
    val bodyRegex = "#.{4}##.{4}##.{4}###".toRegex()
    val tailRegex = ".(#..){6}.".toRegex()
    val monsterMatches = middle().flatMapIndexed { i, line ->
        bodyRegex.findOverlapping(line).mapNotNull { match ->
            match.takeIf {
                get(i).slice(it.range).matches(headRegex) &&
                        get(i + 2).slice(it.range).matches(tailRegex)
            }?.let { i to it }
        }
    }
    return monsterMatches.size
}

private fun <T> List<T>.middle() = slice(1 until lastIndex)
private fun String.middle() = slice(1 until lastIndex)

fun Regex.findOverlapping(string: String): List<MatchResult> {
    val match = find(string) ?: return listOf()
    val matches = mutableListOf(match)
    while(true) {
        val nextMatch = find(string, matches.last().range.first + 1)
        if (nextMatch == null || matches.last() == nextMatch) return matches.toList()
        matches.add(nextMatch)
    }
}