package com.bartenkelaar.year2022.packing

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.of

val moveRegex = """move (\d+) from (\d) to (\d)""".toRegex()

data class Move(val from: Int, val to: Int, val amount: Int) {
    fun applyTo(stacks: List<Stack>) {
        val boxes = stacks[from].takeBoxes(amount)
        boxes.forEach { stacks[to].add(it) }
    }

    fun applyThree(stacks: List<Stack>) {
        val boxes = stacks[from].takeBoxes(amount)
        stacks[to].addAll(boxes)
    }
}

data class Stack(private val boxes: MutableList<Char> = mutableListOf()) {
    fun add(box: Char) {
        if (box != ' ') boxes.add(0, box)
    }

    fun addAll(addedBoxes: List<Char>) {
        boxes.addAll(0, addedBoxes)
    }

    fun takeBoxes(amount: Int): List<Char> {
        val taken = boxes.take(amount)
        repeat(taken.size) { boxes.removeFirst() }
        return taken
    }

    fun top() = boxes.first().toString()
}

class HanoiStacking : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val stackBottom = input.indexOfFirst { it.trim().startsWith("1") }
        val stacks = input.readInitialStacks(stackBottom)
        val moreStacks = input.readInitialStacks(stackBottom)
        val moves = input.subList(stackBottom + 1, input.lastIndex)
            .nonBlank()
            .map { moveRegex.matchEntire(it)!!.groupValues }
            .map { (_, a, f, t) -> Move(f.toInt() - 1, t.toInt() - 1, a.toInt()) }

        moves.forEach {
            it.applyTo(stacks)
            it.applyThree(moreStacks)
        }

        return stacks.readOut() to moreStacks.readOut()
    }

    private fun List<Stack>.readOut() = joinToString("") { it.top() }

    private fun List<String>.readInitialStacks(stackBottom: Int): List<Stack> {
        // assuming amount of stacks < 10 here.
        val stackCount = this[stackBottom].trim().last().toString().toInt()
        val stacks = stackCount.of { Stack() }
        for (i in (stackBottom - 1).downTo(0)) {
            for (j in 0 until stackCount) {
                stacks[j].add(this[i][4 * j + 1])
            }
        }
        return stacks;
    }
}