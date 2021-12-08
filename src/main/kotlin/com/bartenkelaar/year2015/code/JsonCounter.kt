package com.bartenkelaar.year2015.code

import com.bartenkelaar.util.Solver
import com.google.gson.Gson

class JsonCounter : Solver() {
    private val gson = Gson()

    override fun solve(input: List<String>): Pair<Number, Any> {
        val sum = """-?\d+""".toRegex().findAll(input.first()).sumOf { it.value.toInt() }
        val filteredSum = sum(gson.fromJson(input.first(), Map::class.java))

        return sum to filteredSum
    }

    private fun sum(json: Map<*, *>) = if ("red" in json.values) 0 else json.values.sumOf { sum(it!!) }

    private fun sum(v: Any): Int = when(v) {
        is Map<*,*> -> sum(v)
        is List<*> -> v.sumOf { sum(it!!) }
        is Number -> v.toInt()
        else -> 0
    }
}
