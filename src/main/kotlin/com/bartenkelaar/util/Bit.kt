package com.bartenkelaar.util

@JvmInline
value class Bit(val set: Boolean) {
    constructor(bitValue: Char) : this(bitValue.apply { require(this in "01") } == '1')

    val char get() = if (set) '1' else '0'

    override fun toString() = char.toString()
}

fun List<Bit>.toInt() = joinToString("") { it.toString() }.toInt(2)