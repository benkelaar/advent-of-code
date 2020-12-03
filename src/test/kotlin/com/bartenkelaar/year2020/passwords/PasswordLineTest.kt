package com.bartenkelaar.year2020.passwords

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PasswordLineTest {
    @Test
    fun `When parsing a line split it correctly into information`() {
        val line = "2-4 s: password"

        assertEquals(PasswordLine(2, 4, 's', "password"), parseLine(line))
    }

    @Test
    fun `Parse a line with multiple digits correctly`() {
        val line = "20-400 s: password"

        assertEquals(PasswordLine(20, 400, 's', "password"), parseLine(line))
    }

    @Test
    fun `When testing a line for sled rental verify password correctly`() {
        val line = "2-4 s: password"

        assertTrue(parseLine(line)!!.isValidSledRental())
    }

    @Test
    fun `When testing a line for sled rental that has too little chars mark as invalid password correctly`() {
        val line = "2-4 s: pasword"

        assertFalse(parseLine(line)!!.isValidSledRental())
    }

    @Test
    fun `When testing a line for sled rental that has too many chars mark as invalid password correctly`() {
        val line = "2-4 s: passsssword"

        assertFalse(parseLine(line)!!.isValidSledRental())
    }

    @Test
    fun `When testing a line for Toboggan verify password correctly with char in high position`() {
        val line = "2-4 s: password"

        assertTrue(parseLine(line)!!.isValidToboggan())
    }

    @Test
    fun `When testing a line for Toboggan verify password correctly with char in low position`() {
        val line = "2-4 a: password"

        assertTrue(parseLine(line)!!.isValidToboggan())
    }

    @Test
    fun `When testing a line for Toboggan false if both equal`() {
        val line = "3-4 s: password"

        assertFalse(parseLine(line)!!.isValidToboggan())
    }
}
