package com.bartenkelaar.year2020.passports

import kotlin.test.Test
import kotlin.test.assertEquals

class PassportTest {
    @Test
    fun `given enough information, create passport`() {
        val lines = listOf("ecl:gry pid:860033327 eyr:2020 hcl:#fffffd", "byr:1937 iyr:2017 cid:147 hgt:183cm")

        val passport = PassPort.forLines(lines)

        assertEquals(8, passport.data.size)
    }
}
