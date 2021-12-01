package com.bartenkelaar.year2015.mining

import com.bartenkelaar.util.Solver
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

class AdventCoinMiner : Solver() {
    private val digest = MessageDigest.getInstance("md5")

    override fun solve(input: List<String>): Pair<Number, Any> {
        val preface = input.first()
        var hex = "123456"
        var number = 0
        var fiveNumber: Int? = null
        while(!hex.startsWith("000000")) {
            val testValue = (preface + ++number)
            hex = digest.digest(testValue.toByteArray(UTF_8)).toHex()
            if (hex.startsWith("00000") && fiveNumber == null) {
                fiveNumber = number;
            }
        }
        return fiveNumber!! to number
    }

    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
}