package com.bartenkelaar.util

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun Int.rot(n: Int) = mod(n).takeIf { it != 0 } ?: n

fun Int.distanceTo(o: Int) = (this - o).absoluteValue

fun IntRange.grow() = first - 1..last + 1

fun gcd(a: Int, b: Int): Int = gcd(a.toLong(), b.toLong()).toInt()

tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(min(a, b), max(a, b) % min(a, b))

fun lcm(a: Long, b: Long) = a * b / gcd(a, b)

fun List<Int>.product() = reduceRight(Int::times)

fun <T> List<T>.productOf(selector: (T) -> Int) = map(selector).product()
