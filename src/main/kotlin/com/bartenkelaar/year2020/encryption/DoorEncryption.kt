package com.bartenkelaar.year2020.encryption

import com.bartenkelaar.Solver
import com.bartenkelaar.util.nonBlank

class DoorEncryption : Solver {
    override fun solve(input: List<String>): Pair<Number, Any> {
        val (cardPublicKey, doorPublicKey) = input.nonBlank().map { it.toInt() }
        val cardLoopSize = findLoopSize(cardPublicKey)
        val doorLoopSize = findLoopSize(doorPublicKey)
        val encryptionKey = doorPublicKey.encrypt(cardLoopSize)
        return encryptionKey to 0
    }

    private fun findLoopSize(target: Int): Int {
        var result = 1
        var loopSize = 0
        while (result != target) {
            result = result.transform(7)
            loopSize++
        }
        return loopSize
    }

    private fun Number.transform(subject: Int) = ((this.toLong() * subject.toLong()) % 20201227).toInt()

    private fun Int.encrypt(loopSize: Int): Int {
        var result = 1
        for(i in 0 until loopSize) result = result.transform(this)
        return result
    }
}
