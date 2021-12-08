package com.bartenkelaar.util

fun <T> List<T>.shiftSo(value: T, targetIndex: Int): List<T> {
    val currentIndex = indexOf(value)
    val difference = targetIndex - currentIndex
    val split = (lastIndex - difference) % size
    return slice(split + 1..lastIndex) + slice(0 .. split)
}

fun <T> List<T>.nextAfter(current: T, offset: Int = 1) = get((indexOf(current) + offset) % size)
fun <T> List<T>.nextFrom(index: Int, offset: Int = 1) = get((index + offset) % size)

fun <T> List<T>.tail() = slice(1..lastIndex)

fun <T> List<List<T>>.pivot(): List<List<T>> {
    require(all { it.size == first().size })
    return first().mapIndexed { i, _ -> map { it[i] } }
}

infix fun <T> Int.of(element: T) = this of { element }
infix fun <T> Int.of(element: () -> T) = (0 until this).map { element() }