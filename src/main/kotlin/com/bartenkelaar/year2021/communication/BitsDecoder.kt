package com.bartenkelaar.year2021.communication

import com.bartenkelaar.util.Solver
import java.lang.Integer.min

private data class PacketHeader(
    val version: Int,
    val typeId: Int,
    val bitLength: Int
)

private sealed interface Packet {
    val header: PacketHeader

    fun versionSum(): Int
    fun evaluate(): Long
}

private data class Operator(
    override val header: PacketHeader,
    private val subPackets: List<Packet>
) : Packet {
    constructor(version: Int, typeId: Int, bitLength: Int, subPackets: List<Packet>) :
            this(PacketHeader(version, typeId, bitLength), subPackets)

    override fun versionSum() = header.version + subPackets.sumOf { it.versionSum() }
    override fun evaluate() = when (header.typeId) {
        0 -> subPackets.sumOf { it.evaluate() }
        1 -> subPackets.fold(1L) { a, p -> a * p.evaluate() }
        2 -> subPackets.minOf { it.evaluate() }
        3 -> subPackets.maxOf { it.evaluate() }
        5 -> if (subPackets[0].evaluate() > subPackets[1].evaluate()) 1 else 0
        6 -> if (subPackets[0].evaluate() < subPackets[1].evaluate()) 1 else 0
        7 -> if (subPackets[0].evaluate() == subPackets[1].evaluate()) 1 else 0
        else -> throw RuntimeException(header.toString())
    }
}

private data class LiteralValue(
    override val header: PacketHeader,
    private val value: Long
) : Packet {
    constructor(version: Int, bitLength: Int, value: Long) : this(PacketHeader(version, 4, bitLength), value)

    override fun versionSum() = header.version
    override fun evaluate() = value
}

class BitsDecoder : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val packet = input.first()
        val bits = packet.toList()
            .joinToString("") {
                it.toString().toInt(16).toString(2).padStart(4, '0')
            }

        val mainPacket = readPacket(bits)
        return mainPacket.versionSum() to mainPacket.evaluate()
    }

    private fun readPacket(bits: String): Packet {
        val version = bits.substring(0, 3).toInt(2)
        val typeId = bits.substring(3, 6).toInt(2)
        val nextBits = bits.substring(6)
        return if (typeId == 4) readLiteral(version, nextBits)
        else readOperator(version, typeId, nextBits)
    }

    private fun readLiteral(version: Int, bits: String): LiteralValue {
        var blockCount = 0
        var valueString = ""
        do {
            valueString += bits.substring(blockCount * 5 + 1, (blockCount + 1) * 5)
        } while (bits[blockCount++ * 5] == '1')
        return LiteralValue(version, 5 * blockCount + 6, valueString.toLong(2))
    }

    private fun readOperator(version: Int, typeId: Int, bits: String) =
        if (bits.first() == '1') {
            val subPacketCount = bits.substring(1, 12).toInt(2)
            val subPackets = readPackets(subPacketCount, bits.substring(12))
            Operator(version, typeId, subPackets.sumOf { it.header.bitLength } + 18, subPackets)
        } else {
            val packetLength = min(22 + bits.substring(1, 16).toInt(2), bits.length)
            val subPackets = readPackets(Int.MAX_VALUE, bits.substring(16, packetLength))
            Operator(version, typeId, packetLength, subPackets)
        }

    private tailrec fun readPackets(targetCount: Int, bits: String, packets: List<Packet> = listOf()): List<Packet> =
        if (targetCount == packets.size || bits.length < 11) packets
        else {
            val packet = readPacket(bits)
            readPackets(targetCount, bits.substring(packet.header.bitLength), packets + packet)
        }
}
