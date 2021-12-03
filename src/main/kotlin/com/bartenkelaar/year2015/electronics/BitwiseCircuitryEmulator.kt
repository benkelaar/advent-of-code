package com.bartenkelaar.year2015.electronics

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.isPositiveNumber
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.year2015.electronics.Gate.*
import kotlin.math.pow

private val CONNECTION_REGEX = """([a-z0-9]*?) ?(AND|OR|[LR]SHIFT|NOT|) ?([a-z]+|\d+) -> ([a-z]+)""".toRegex()

data class CircuitryConnection(val input1: String?, val input2: String, val gate: Gate, val output: String) {
    val inputWires = input2.toWireList() + (input1?.toWireList() ?: emptyList())

    fun calculateCurrent(currents: Map<String, UShort>) = output to when(gate) {
        IN -> currents.lookup(input2)
        AND -> currents.lookup(input1) and currents.lookup(input2)
        OR -> currents.lookup(input1) or currents.lookup(input2)
        NOT -> currents.lookup(input2) xor UShort.MAX_VALUE
        LSHIFT -> (currents.lookup(input1) * twoToThePower(input2)).toUShort()
        RSHIFT -> (currents.lookup(input1) / twoToThePower(input2)).toUShort()
    }

    private fun twoToThePower(power: String) = 2.0.pow(power.toInt()).toUInt()

    private fun Map<String, UShort>.lookup(input: String?) = input?.takeIf { it.isPositiveNumber() }?.toUShort() ?: getValue(input!!)

    private fun String.toWireList() = takeIf { !it.isPositiveNumber() }?.let { listOf(it) } ?: emptyList()

    companion object {
        fun parseFrom(line: String): CircuitryConnection {
            val (input1, gate, input2, output) = CONNECTION_REGEX.matchEntire(line)?.destructured!!
            return CircuitryConnection(
                input1.takeIf { it.isNotBlank() },
                input2,
                gate.takeIf { it.isNotBlank() }?.let { Gate.valueOf(it) } ?: IN,
                output)
        }
    }
}

enum class Gate { IN, AND, OR, NOT, LSHIFT, RSHIFT }

class BitwiseCircuitryEmulator : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val connections = input.nonBlank().map { CircuitryConnection.parseFrom(it) }

        val aWireCurrent = connections.runCircuit()
        val newConnections = connections.filter { it.output != "b" } + CircuitryConnection(null, aWireCurrent.toString(), IN, "b")
        val newAWireCurrent = newConnections.runCircuit()
        return aWireCurrent to newAWireCurrent
    }

    private fun List<CircuitryConnection>.runCircuit(): Int {
        val handledConnections = toMutableSet()
        var currentConnections = handledConnections.filter { it.inputWires.isEmpty() }.toSet()
        val currents = currentConnections.associate { it.output to it.input2.toUShort() }.toMutableMap()
        while ("a" !in currents) {
            handledConnections -= currentConnections
            currentConnections = handledConnections.filter { it.inputWires.all { wire -> wire in currents } }.toSet()
            currents += currentConnections.associate { it.calculateCurrent(currents) }
        }
        return currents["a"]!!.toInt()
    }
}