package com.bartenkelaar.year2020.construction

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import kotlin.math.abs

data class HexCoordinate(val x: Int, val y: Int) {
    fun move(direction: HexDirection) = direction.move(this)

    fun getNeighbours() = HexDirection.values().map { move(it) }

    fun neighbourCount(blackTiles: Set<HexCoordinate>) = getNeighbours().count { it in blackTiles }
}

enum class HexDirection(private val mover: HexCoordinate.() -> HexCoordinate) {
    NW({ HexCoordinate(x - abs(y % 2), y + 1) }),
    NE({ HexCoordinate(x + 1 - abs(y % 2), y + 1) }),
    E({ copy(x = x + 1) }),
    SE({ HexCoordinate(x + 1 - abs(y % 2), y - 1) }),
    SW({ HexCoordinate(x - abs(y % 2), y - 1) }),
    W({ copy(x = x - 1) });

    fun move(from: HexCoordinate) = from.mover()

    companion object {
        fun listFromInput(input: String): List<HexDirection> {
            val result = mutableListOf<HexDirection>()
            var temp = ""
            for (c in input.uppercase()) {
                if (c in "WE") {
                    result += valueOf(temp + c)
                    temp = ""
                } else temp += c
            }
            return result
        }
    }
}

class FloorPatternBuilder : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val blackTiles = input.nonBlank()
            .map(HexDirection.Companion::listFromInput)
            .map { it.fold(HexCoordinate(0, 0), HexCoordinate::move) }
            .groupBy { it }
            .filter { (_, v) -> v.size % 2 == 1 }
            .keys

        var conwayTiles = blackTiles
        for (i in 0 until 100) {
            val possibleFlipCoordinates = (conwayTiles.flatMap { it.getNeighbours() } + conwayTiles).toSet()
            conwayTiles = possibleFlipCoordinates.map { it to it.neighbourCount(conwayTiles) }
                .filter { (c, neighbourCount) -> neighbourCount == 2 || (c in conwayTiles && neighbourCount == 1) }
                .toMap().keys
        }

        return blackTiles.size to conwayTiles.size
    }
}