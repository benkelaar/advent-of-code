package com.bartenkelaar.year2020.navigation

import com.bartenkelaar.Solver

class DockCommunicator: Solver {
    override fun solve(input: List<String>): Pair<Number, Number> {
        return valueMask(input) to addressMask(input)
    }

    private fun addressMask(input: List<String>): Number {
        var mask = mapOf<Int, Char>()
        val memory = mutableMapOf<Long, Long>()
        for (line in input) {
            if (line.startsWith("mask")) {
                mask = parseMask(line.trim(*"mask =".toCharArray()), '0')
            } else if (line.startsWith("mem")) {
                val (addressValue, value) = parseLine(line)
                val addresses = findAddresses(addressValue, mask)
                addresses.forEach { address ->
                    memory[address] = value.toLong()
                }
            }
        }
        return memory.values.sum()
    }

    private fun findAddresses(address: String, mask: Map<Int, Char>): List<Long> {
        val addressBits = address.toLong().toString(2).toCharArray().toMutableList()
        val addresses = mutableListOf(initialize(addressBits))
        mask.forEach { (i, char) ->
            val newAddresses = mutableListOf<MutableList<Char>>()
            addresses.forEach {
                it[i] = if (char == 'X') '0' else char
                if (char == 'X') {
                    val newAddress = mutableListOf(*it.toTypedArray())
                    newAddress[i] = '1'
                    newAddresses += newAddress
                }
            }
            addresses += newAddresses
        }
        return addresses.map { it.joinToString("").toLong(2) }
    }

    private fun valueMask(input: List<String>): Long {
        var mask = mapOf<Int, Char>()
        val memory = mutableMapOf<Long, Long>()
        for (line in input) {
            if (line.startsWith("mask")) {
                mask = parseMask(line.trim(*"mask =".toCharArray()), 'X')
            } else if (line.startsWith("mem")) {
                val (address, value) = parseLine(line)
                memory[address.toLong()] = mask.applyTo(value.toLong())
            }
        }
        return memory.values.sum()
    }

    private fun parseLine(line: String) = line.trim(*"mem[ ".toCharArray()).split("] = ")

    private fun parseMask(mask: String, filter: Char) =
        mask.toCharArray()
            .mapIndexed { i, char -> i to char }
            .filter { it.second != filter }
            .toMap()
            .mapValues { entry -> entry.value }
}

private fun Map<Int, Char>.applyTo(value: Long): Long {
    val bits = initialize(value.toString(2).toCharArray().toMutableList())
    forEach { (i, map) ->
        bits[i] = map
    }
    return bits.joinToString("").toLong(2)
}

private fun initialize(valueList: MutableList<Char>): MutableList<Char> {
    return if (valueList.lastIndex < 35) {
        val prefix = CharArray(35 - valueList.lastIndex)
        prefix.fill('0')
        (prefix + valueList.toCharArray()).toMutableList()
    } else valueList
}