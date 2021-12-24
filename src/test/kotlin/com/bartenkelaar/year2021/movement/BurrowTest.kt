package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.of
import com.bartenkelaar.year2021.movement.Amphipod.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BurrowTest {
    @Test
    fun `given D & A switched, expect 8008 potential energy`() {
        val burrow = burrow(amberSlot = listOf(DESERT, AMBER), desertSlot = listOf(AMBER, DESERT))

        assertEquals(8008, burrow.potentialEnergy())
    }

    @Test
    fun `given B & C bottoms switched, expect 880 potential energy`() {
        val burrow = burrow(bronzeSlot = listOf(BRONZE, COPPER), copperSlot = listOf(COPPER, BRONZE))

        assertEquals(880, burrow.potentialEnergy())
    }

    @Test
    fun `given hallway position, expect right potential energy`() {
        val burrow = burrow(hallway(0 to DESERT, 10 to AMBER),
            amberSlot = listOf(null, AMBER),
            desertSlot = listOf(null, DESERT)
        )

        assertEquals(9009, burrow.potentialEnergy())
    }

    @Test
    fun `completed has 0 potential energy`() {
        assertEquals(0, burrow().potentialEnergy())
    }

    private fun hallway(vararg replacements: Pair<Int, Amphipod>) = with(replacements.toMap()) {
        Hallway((11 of null).mapIndexed { i, pod -> getOrElse(i) { pod } } )
    }

    private fun burrow(
        hallway: Hallway = Hallway(),
        amberSlot: List<Amphipod?> = 2 of AMBER,
        bronzeSlot: List<Amphipod?> = 2 of BRONZE,
        copperSlot: List<Amphipod?> = 2 of COPPER,
        desertSlot: List<Amphipod?> = 2 of DESERT,
    ) = Burrow(hallway, listOf(Slot(amberSlot, AMBER), Slot(bronzeSlot, BRONZE), Slot(copperSlot, COPPER), Slot(desertSlot, DESERT)))
}