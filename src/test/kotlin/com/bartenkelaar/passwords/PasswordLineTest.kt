package com.bartenkelaar.passwords

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PasswordLineTest {
    @Test
    fun `Skip empty line`() {

    }

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
    fun `When testing a line verify password correctly`() {
        val line = "2-4 s: password"

        assertTrue(parseLine(line)!!.isValid())
    }

    @Test
    fun `When testing a line that hass too little chars mark as invalid password correctly`() {
        val line = "2-4 s: pasword"

        assertFalse(parseLine(line)!!.isValid())
    }

    @Test
    fun `When testing a line that hass too many chars mark as invalid password correctly`() {
        val line = "2-4 s: passsssword"

        assertFalse(parseLine(line)!!.isValid())
    }
}
