package com.bartenkelaar.year2015.electronics

import com.bartenkelaar.year2015.electronics.LightAction.ON
import org.junit.jupiter.api.Test

class InstructionTest {
    @Test
    fun `Turning four lights on works`() {
        val lights = arrayOf(booleanArrayOf(false, false), booleanArrayOf(false, false))
        val dimmableLights = arrayOf(intArrayOf(0, 0), intArrayOf(0, 0))
        val instruction = Instruction(ON, LightCoordinate(0, 0), LightCoordinate(1,1))

        instruction.applyTo(lights, dimmableLights)

        check(lights.all { row -> row.all { it } })
        check(dimmableLights.all { row -> row.all { it == 1 } })
    }
}