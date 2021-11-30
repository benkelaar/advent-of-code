package com.bartenkelaar.year2020.encryption

import kotlin.test.Test
import kotlin.test.assertEquals

class DoorEncryptionTest {
    private val encryption = DoorEncryption()

    @Test
    fun exampleTest() {
        val input = listOf(
            "5764801",
            "17807724",
        )

        assertEquals(14897079, encryption.solve(input).first)
    }
}