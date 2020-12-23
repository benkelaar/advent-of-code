package com.bartenkelaar.util

fun <T> Collection<T>.only() = first().takeIf { size == 1}!!

fun <T> List<T>.shiftSo(value: T, targetIndex: Int): List<T> {
    val currentIndex = indexOf(value)
    val difference = targetIndex - currentIndex
    val split = (lastIndex - difference) % size
    return slice(split + 1..lastIndex) + slice(0 .. split)
}

fun <T> List<T>.nextAfter(current: T, offset: Int = 1) = get((indexOf(current) + offset) % size)
fun <T> List<T>.nextFrom(index: Int, offset: Int = 1) = get((index + offset) % size)

fun <T> List<T>.tail() = slice(1..lastIndex)
