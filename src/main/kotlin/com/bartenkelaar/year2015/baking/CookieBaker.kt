package com.bartenkelaar.year2015.baking

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import com.bartenkelaar.util.tail
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.absoluteValue

private data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)

@JvmInline
private value class Recipe(val ingredients: Map<Ingredient, Int>) {
    fun score() = score { capacity } * score { durability } * score { flavor } * score { texture }

    fun improve(a: Ingredient, b: Ingredient) = listOf(this, adjust(a, b), adjust(b, a)).maxByOrNull { it.score() }!!
    fun caloryImprove(a: Ingredient, b: Ingredient): Recipe {
        return this
    }

    fun calorySum() = ingredients.map { (i, a) -> a * i.calories }.sum()

    private fun adjust(from: Ingredient, to: Ingredient) = Recipe(ingredients.mapValues { (i, a) -> when(i) {
        from -> a - 1
        to -> a + 1
        else -> a
    } })

    private fun score(aspect: Ingredient.() -> Int) =
        max(ingredients.map { (i, a) -> a * i.aspect() }.sum(), 0)
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

8yz(16wx-5w^2-5wy-3x^2+xy-3zx+zw+zy)

5w + x + 6y + 8z = 500
4w + 5y + 7z = 400

 */
class CookieBaker : Solver() {
    private val inputRegex = """(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""".toRegex()

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

        var recipe = Recipe(ingredients.associateWith { 25 })
        do {
            val previousRecipe = recipe
            recipe = recipe.improve(ingredients[0], ingredients[1])
            recipe = recipe.improve(ingredients[0], ingredients[2])
            recipe = recipe.improve(ingredients[0], ingredients[3])
            recipe = recipe.improve(ingredients[1], ingredients[2])
            recipe = recipe.improve(ingredients[1], ingredients[3])
            recipe = recipe.improve(ingredients[2], ingredients[3])
        } while(previousRecipe.score() != recipe.score())

        /**
         * IdemCaloric change rules:
         *
         * -3Spr  +PB  +FR  +Su
         * +Spr   +PB -5Fr +3Su
         * +7Spr -3PB      -4Su
         */
        val caloryRecipe = Recipe(mapOf(
            ingredients.first() to 27,
            ingredients[1] to 27,
            ingredients[2] to 15,
            ingredients[3] to 31,
        ))

        return recipe.score() to caloryRecipe.score()
    }
}