package com.bartenkelaar.year2024.operation

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.of

class DiskDefragmenter : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val compacted = input.first().toCharArray().map { it.toString().toInt() }

        val sum = compacted.defragmentPerFileSum()
        val blockSum = compacted.defragmentPerBlockSum()

        return sum to blockSum
    }

    private fun List<Int>.defragmentPerFileSum(): Long {
        val records = filterIndexed { i, _ -> i % 2 == 0 }
        val resultLength = records.sum()
        val recordWeights = records.flatMapIndexed { i, b -> b of i }

        var i = 0
        var compactIndex = 0
        var recordIndex = 0
        var spaceIndex = recordWeights.lastIndex
        var sum = 0L
        var record = true

        while (i < resultLength) {
            val blockSize = get(compactIndex++)
            val lastI = (i + blockSize).coerceAtMost(resultLength)
            while (i < lastI) {
                sum += i++ * recordWeights[if (record) recordIndex++ else spaceIndex--]
            }
            record = !record
        }
        return sum
    }

    private fun List<Int>.defragmentPerBlockSum(): Long {
        val movedBlocks = moveBlocks()
        val movedRecordsIndices = movedBlocks.flatMap { (_, blocks) -> blocks.map { (_, i) -> i } }.toSet()

        var sum = 0L
        var memoryIndex = 0
        for (i in indices) {
            val record = i % 2 == 0
            val recordIndex = i / 2
            val blockSize = get(i)
            val targetIndex = memoryIndex + blockSize
            if (record) {
                if (recordIndex !in movedRecordsIndices) {
                    sum += blockWeight(memoryIndex.toLong(), memoryIndex.toLong() + blockSize) * recordIndex
                }
            } else for ((movedRecordSize, movedRecordIndex) in movedBlocks.getOrDefault(i, emptyList())) {
                sum += blockWeight(memoryIndex.toLong(), memoryIndex.toLong() + movedRecordSize) * movedRecordIndex
                memoryIndex += movedRecordSize
            }
            memoryIndex = targetIndex
        }

        return sum
    }

    private fun List<Int>.moveBlocks(): Map<Int, List<Pair<Int, Int>>> {
        val (records, spaces) = mapIndexed { i, s -> i to s }.partition { (i, _) -> i % 2 == 0 }
        val consumed = mutableMapOf<Int, List<Pair<Int, Int>>>()
        records.indices.reversed().forEach { recordIndex ->
            val (_, blockSize) = records[recordIndex]
            val target = spaces
                .slice(0 until recordIndex)
                .firstOrNull { it.canHouse(blockSize, consumed[it.first]) }

            if (target != null) {
                consumed[target.first] =
                    consumed.getOrDefault(target.first, emptyList()) + listOf(blockSize to recordIndex)
            }
        }
        return consumed.toMap()
    }

    private fun Pair<Int, Int>.canHouse(blockSize: Int, consumed: List<Pair<Int, Int>>?) =
        second - (consumed?.sumOf { it.first } ?: 0) >= blockSize

    private fun blockWeight(from: Long, to: Long): Long = to * (to - 1) / 2 - from * (from - 1) / 2
}
