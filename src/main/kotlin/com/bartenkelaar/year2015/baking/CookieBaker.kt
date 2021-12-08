package com.bartenkelaar.year2015.baking

import com.bartenkelaar.util.Solver

private data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)

class CookieBaker : Solver() {
    override fun solve(input: List<String>): Pair<Number, Any> {
        return 0 to 0
    }
}

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