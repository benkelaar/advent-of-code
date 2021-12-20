package com.bartenkelaar.year2015.baking

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank

private data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)

/**
Sprinkles: capacity 5, durability -1, flavor 0, texture 0, calories 5
PeanutButter: capacity -1, durability 3, flavor 0, texture 0, calories 1
Frosting: capacity 0, durability -1, flavor 4, texture 0, calories 6
Sugar: capacity -1, durability 0, flavor 0, texture 2, calories 8

w + x + y + z = 100
5w > x + z
3x > w + y
(5w-x-z)*(3x-w-y)*4y*2z = C

 *
 *
 *
 */
class CookieBaker : Solver() {
    val inputRegex = """(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""".toRegex()

    override fun solve(input: List<String>): Pair<Number, Any> {
        val ingredients = input.nonBlank()
            .map { inputRegex.matchEntire(it)!!.destructured }
            .map { (name, capacity, durability, flavor, texture, calories) -> Ingredient(
                name = name,
                capacity = capacity.toInt(),
                durability = durability.toInt(),
                flavor = flavor.toInt(),
                texture = texture.toInt(),
                calories = calories.toInt()
            ) }
        return 0 to 0
    }
}