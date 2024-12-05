package com.bartenkelaar.year2015.code

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.splitWhiteSpace
import com.bartenkelaar.year2015.code.Stat.ARMOUR
import com.bartenkelaar.year2015.code.Stat.DAMAGE

enum class Stat { DAMAGE, ARMOUR }

data class Item(
    val name: String,
    val bonus: Int,
    val cost: Int,
)

data class Ring(
    private val info: Item,
    val affects: Stat,
) {
    fun bonus() = info.bonus
}

data class Fighter(
    val hp: Int,
    val baseDamage: Int,
    val baseArmour: Int,
    val weapon: Item? = null,
    val armour: Item? = null,
    val rings: Set<Ring> = emptySet(),
) {
    fun damage() = baseDamage + (weapon?.bonus ?: 0) + ringBonus(DAMAGE)

    fun armour() = baseDamage + (armour?.bonus ?: 0) + ringBonus(ARMOUR)

    private fun ringBonus(stat: Stat) = rings.filter { it.affects == stat }.sumOf { it.bonus() }
}

class RpgFighter : Solver() {
    val weapons =
        listOf(
            Item("Dagger", 4, 8),
            Item("Shortsword", 5, 10),
            Item("Warhammer", 6, 25),
            Item("Longsword", 7, 40),
            Item("Greataxe", 8, 74),
        )

    val armour =
        listOf(
            Item("Leather", 1, 13),
            Item("Chainmail", 2, 31),
            Item("Splintmail", 3, 53),
            Item("Bandedmail", 4, 75),
            Item("Platemail", 5, 102),
        )

    val rings =
        listOf(
            Ring(Item("Damage +1", 25, 1), DAMAGE),
            Ring(Item("Damage +2", 50, 2), DAMAGE),
            Ring(Item("Damage +3", 100, 3), DAMAGE),
            Ring(Item("Defense +1", 20, 1), ARMOUR),
            Ring(Item("Defense +2", 40, 2), ARMOUR),
            Ring(Item("Defense +3", 80, 3), ARMOUR),
        )

    override fun solve(input: List<String>): Pair<Any, Any> {
        val (hp, damage, armour) = input.nonBlank().map { it.value() }
        val monster = Fighter(hp, damage, armour)

        val basePlayer = Fighter(100, 0, 0)
        return 0 to 0
    }

    private fun String.value() = splitWhiteSpace().last().toInt()
}
