package com.bartenkelaar.year2024.communication

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver

class AntennaTuner : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val antennaGrid = Grid
            .forChars(input)
        val antennas = antennaGrid
            .mapCoordinated { c, a -> c to a }
            .rows
            .flatten()
        val frequencies = antennas.unzip().second.toSet() - '.'
        val antiNodes = frequencies.associate {
            it to antennas.countAntiNodes(it, antennaGrid)
        }
        val frequenciesA = antiNodes.values
            .flatten()
            .toSet()
            .size
        val antiNodesB = frequencies.associate {
            it to antennas.countResonantAntiNodes(it, antennaGrid)
        }
        val frequenciesB = antiNodesB.values
            .flatten()
            .toSet()
            .size
        return frequenciesA to frequenciesB
    }

    private fun List<Pair<Coordinate, Char>>.countAntiNodes(char: Char, antennaGrid: Grid<Char>): Set<Coordinate> {
        val antennas = filter { it.second == char }

        val resonancePoints = antennas.mapIndexed { i, (c, _) ->
            antennas
                .subList(i + 1, antennas.size)
                .map { (co, _) ->
                    val distance = c - co
                    val resonanceOne = co - distance
                    val resonanceTwo = c + distance
                    listOf(resonanceOne, resonanceTwo).filter { it in antennaGrid }
                }.toFlatSet()
        }

        return resonancePoints.toFlatSet()
    }

    private fun List<Pair<Coordinate, Char>>.countResonantAntiNodes(char: Char, antennaGrid: Grid<Char>): Set<Coordinate> {
        val antennas = filter { it.second == char }

        val resonancePoints = antennas.mapIndexed { i, (c, _) ->
            antennas
                .subList(i + 1, antennas.size)
                .map { (co, _) ->
                    val distance = c - co
                    val resonanceNodes = mutableSetOf<Coordinate>()
                    var next = c
                    do {
                        next = next + distance
                        if (next in antennaGrid) resonanceNodes += next
                    } while (next in antennaGrid)
                    next = co
                    do {
                        next = next - distance
                        if (next in antennaGrid) resonanceNodes += next
                    } while (next in antennaGrid)
                    return@map resonanceNodes + setOf(c, co)
                }.toFlatSet()
        }

        return resonancePoints.toFlatSet()
    }

    fun <T> Collection<Collection<T>>.toFlatSet() = flatten().toSet()
}
