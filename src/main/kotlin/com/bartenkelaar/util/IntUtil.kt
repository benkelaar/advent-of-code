package com.bartenkelaar.util

import kotlin.math.absoluteValue

fun Int.rot(n: Int) = mod(n).takeIf { it != 0 } ?: n
fun Int.distanceTo(o: Int) = (this - o).absoluteValue

fun IntRange.grow() = first - 1..last + 1

tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(Integer.min(a, b), Integer.max(a, b) % Integer.min(a, b))
fun lcm(a: Int, b: Int) = a * b / gcd(a, b)

fun List<Int>.product() = reduceRight(Int::times)

fun <T> List<T>.productOf(selector: (T) -> Int) = map(selector).product()