package com.bartenkelaar.year2022.gaming

import com.bartenkelaar.util.*

private data class Monkey(
    val id: Int,
    val test: Int,
    val trueTarget: Int,
    val falseTarget: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    var activity: Int = 0
) {
    fun inspect(item: Long) = operation(item)
    fun inspectReduce(item: Long) = operation(item) / 3L
    fun test(item: Long) = if (item.mod(test.toLong()) == 0L) trueTarget else falseTarget
    fun pass(new: Long) { items += new }
}

class MonkeyBusiness : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val monkeys = readMonkeys(input)
        val alsoMonkeys = readMonkeys(input)

        val lcm = monkeys.map { it.test }.product()

        val monkeyBusiness = monkeys.monkeyAround(lcm, 20, Monkey::inspectReduce)
        val moreBusiness = alsoMonkeys.monkeyAround(lcm, 10000, Monkey::inspect)
        return monkeyBusiness to moreBusiness
    }

    private fun readMonkeys(input: List<String>) = input.zipPerEmpty().map { monkeyLines ->
        Monkey(
            id = monkeyLines.first().trim(':').lastInt(),
            test = monkeyLines[3].lastInt(),
            trueTarget = monkeyLines[4].lastInt(),
            falseTarget = monkeyLines[5].lastInt(),
            items = monkeyLines[1].substring(18).trim()
                .split(", ").map { it.toLong() }.toMutableList(),
            operation = readOperation(monkeyLines[2])
        )
    }

    private fun List<Monkey>.monkeyAround(lcm: Int, rounds: Int, inspector: Monkey.(Long) -> Long): Long {
        repeat(rounds) {
            forEach { monkey: Monkey ->
                monkey.items.forEach { item ->
                    val new = monkey.inspector(item)
                    val target = this[monkey.test(new)]
                    target.pass(new.mod(lcm).toLong())
                }
                monkey.activity += monkey.items.size
                monkey.items.clear()
            }
        }

        val (mostActiveMonkey, secondMostActiveMonkey) = sortedByDescending { it.activity }
        return mostActiveMonkey.activity.toLong() * secondMostActiveMonkey.activity.toLong()
    }

    private fun readOperation(description: String): (Long) -> Long {
        val operatorValue = description.lastWord()
        return if (operatorValue.isPositiveNumber()) {
            val value = operatorValue.toInt()
            if ("+" in description) ({ old -> old + value }) else ({ old -> old * value })
        } else ({ old -> old * old })
    }
}
