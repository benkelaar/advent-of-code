package com.bartenkelaar.year2022.coms

import com.bartenkelaar.util.Coordinate
import com.bartenkelaar.util.Grid
import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.max

data class Tree(val coordinate: Coordinate, val height: Int, val visible: Boolean = false) : Comparable<Tree> {
    override fun compareTo(other: Tree) = height.compareTo(other.height)
}

class TreeFile : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val trees = Grid.forChars(input)
            .mapCoordinated { c, t -> Tree(c, t.toString().toInt()) }

        val checkedTrees = trees.mapCoordinated { c, tree ->
            tree.copy(visible =
                trees.northOf(c).allLowerThan(tree) ||
                trees.southOf(c).allLowerThan(tree) ||
                trees.westOf(c).allLowerThan(tree) ||
                trees.eastOf(c).allLowerThan(tree)
            )
        }

        val scenicScores = trees.mapCoordinated { c, tree ->
            trees.northOf(c).reversed().countVisibleFrom(tree) *
                    trees.southOf(c).countVisibleFrom(tree) *
                    trees.eastOf(c).countVisibleFrom(tree) *
                    trees.westOf(c).reversed().countVisibleFrom(tree)
        }

        return checkedTrees.count { it.visible } to scenicScores.max()
    }
}

private fun List<Tree>.countVisibleFrom(tree: Tree) = if (isEmpty()) 0L else
        (indexOfFirst { it >= tree }.takeIf { it >= 0 } ?: lastIndex) + 1L

private fun List<Tree>.allLowerThan(tree: Tree) = all { it < tree }

private fun Grid<Tree>.northOf(coordinate: Coordinate): List<Tree> {
    return rows.subList(0, coordinate.y).map { it[coordinate.x] }
}

private fun Grid<Tree>.southOf(coordinate: Coordinate): List<Tree> {
    return rows.subList(coordinate.y + 1, rows.size).map { it[coordinate.x] }
}

private fun Grid<Tree>.westOf(coordinate: Coordinate): List<Tree> {
    return rows[coordinate.y].subList(0, coordinate.x)
}

private fun Grid<Tree>.eastOf(coordinate: Coordinate): List<Tree> {
    return rows[coordinate.y].subList(coordinate.x + 1, rowSize())
}
