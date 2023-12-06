package com.bartenkelaar.util

import kotlin.math.max
import kotlin.math.min

fun <T> List<T>.tail() = allAfter(0)
fun <T> List<T>.allAfter(index: Int) = subList(index + 1, size)

fun List<String>.reverseAll() = map { it.reversed() }

fun <T, U> List<T?>.notNullMapIndexed(mapper: (Int, T) -> U) = mapIndexed { i, v -> v?.let { mapper(i, it) } }.filterNotNull()
fun <T> List<T?>.sumNotNullIndexed(mapper: (Int, T) -> Int) = notNullMapIndexed(mapper).sum()

fun <T> List<T>.partitionPer(breakValue: T) = partitionPer { it == breakValue }
fun <T> List<T>.partitionPer(breakCondition: (T) -> Boolean) =
    (listOf(0) + mapIndexedNotNull { i, value -> i.takeIf { breakCondition(value) } } + size)
        .zipWithNext()
        .map { (f, t) -> subList(f, t)}
        .filter { it.isNotEmpty() }

fun <T> List<List<T>>.pivot(): List<List<T>> {
    require(all { it.size == first().size })
    return List(first().size) { i -> map { it[i] } }
}

fun <T> List<T>.boundedSlice(from: Int, to: Int) = slice(max(0, from)..min(to, lastIndex))

fun <T> List<T>.spindexOf(element: T) = indexOf(element) + 1

infix fun <T> Int.of(element: T) = this of { element }
infix fun <T> Int.of(element: () -> T) = (0 until this).map { element() }