package com.bartenkelaar.util

fun <T> List<T>.tail() = slice(1..lastIndex)

fun <T> List<List<T>>.pivot(): List<List<T>> {
    require(all { it.size == first().size })
    return first().mapIndexed { i, _ -> map { it[i] } }
}

infix fun <T> Int.of(element: T) = this of { element }
infix fun <T> Int.of(element: () -> T) = (0 until this).map { element() }