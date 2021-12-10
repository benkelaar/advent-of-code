package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.tail
import kotlin.math.pow

private sealed interface SyntaxState

private data class Corrupted(val faultyClosure: ChunkType) : SyntaxState
private data class Incomplete(val missingChars: List<ChunkType>) : SyntaxState
private object OK : SyntaxState

private enum class ChunkType(val open: Char, val close: Char, val corruptionPoints: Int, val completionPoints: Int) {
    ROUNDED('(', ')', 3, 1),
    SQUARE('[', ']', 57, 2),
    CURLY('{', '}', 1197, 3),
    POINTY('<', '>', 25137, 4);

    companion object {
        fun forOpen(char: Char) = char.chunkFor { open }
        fun forClose(char: Char) = char.chunkFor { close }

        private fun Char.chunkFor(property: ChunkType.() -> Char) = values().single { it.property() == this }
    }
}

class NavigationFixer : Solver() {
    private val openChars = ChunkType.values().map { it.open }

    override fun solve(input: List<String>): Pair<Any, Any> {
        val lines = input.nonBlank()
        val linesStates = lines.map { classifyLine(emptyList(), it.toList()) }

        val corruptedChunkClosures = linesStates.mapNotNull { if (it is Corrupted) it.faultyClosure else null }
        val corruptionScore = corruptedChunkClosures.sumOf { it.corruptionPoints }

        val lineCompletions = linesStates.mapNotNull { if (it is Incomplete) it.missingChars else null }
        val completionsScores = lineCompletions.map { it.scoreCompletion() }.sorted()
        val middleCompletionScore = completionsScores[(completionsScores.size - 1) / 2]

        return corruptionScore to middleCompletionScore
    }

    private tailrec fun classifyLine(openChunks: List<ChunkType>, line: List<Char>): SyntaxState {
        if (line.isEmpty()) return if (openChunks.isEmpty()) OK else Incomplete(openChunks.reversed())
        if (line.first() in openChars) return classifyLine(openChunks + ChunkType.forOpen(line.first()), line.tail())
        if (line.first() == openChunks.last().close) return classifyLine(openChunks.subList(0, openChunks.lastIndex), line.tail())
        return Corrupted(ChunkType.forClose(line.first()))
    }

    private fun List<ChunkType>.scoreCompletion() =
        reversed().mapIndexed { i, chunkType -> chunkType.completionPoints * 5.0.pow(i).toLong() }.sum()
}
