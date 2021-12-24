package com.bartenkelaar.util

@JvmInline
value class Bit(val set: Boolean) {
    constructor(bitValue: Char) : this(bitValue.apply { require(this in "01") } == '1')
    constructor(bitValue: Char, trueValue: Char) : this(bitValue == trueValue)

    val char get() = if (set) '1' else '0'

    operator fun not() = Bit(!set)
    operator fun invoke() = set

    override fun toString() = char.toString()

    companion object {
        val SET = Bit(true)
    }
}

fun List<Bit>.toInt() = joinToString("") { it.toString() }.toInt(2)
fun Grid<Bit>.count() = count { it() }
fun Grid<Bit>.print() = rows.forEach { println(it.joinToString("") { if (it()) "⬜" else "⬛" }) }