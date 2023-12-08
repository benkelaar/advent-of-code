package com.bartenkelaar.year2023.navigation

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.lcm
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.tail

class DesertMapReader : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val pathChars = input.first.toCharArray()
        val pathIndices = pathChars.map { if (it == 'L') 0 else 1 }
        val nodes = input.subList(2, input.lastIndex).nonBlank()
            .map { it.split(" = ") }
            .associate { (id, branches) -> id to branches.trim('(', ')').split(", ") }

        tailrec fun walkPath(node: List<String>, path: List<Int>, distance: Long = 1, isDestination: (String) -> Boolean): Long {
            val newPath = path.ifEmpty { pathIndices }
            val nextNode = node[newPath.first]
            return if (isDestination(nextNode)) distance
                else walkPath(nodes.getValue(nextNode), newPath.tail(), distance + 1, isDestination)
        }

        val ghostStarts = nodes.mapNotNull { (k, v) -> v.takeIf { k.last() == 'A' } }
        val ghostDistances = ghostStarts.map { node -> walkPath(node, pathIndices) { it.last() == 'Z' } }

        return walkPath(nodes.getValue("AAA"), pathIndices) { it == "ZZZ" } to ghostDistances.reduce(::lcm)
    }
}