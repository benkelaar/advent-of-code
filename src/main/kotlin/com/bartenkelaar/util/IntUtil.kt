package com.bartenkelaar.util

import kotlin.math.absoluteValue

fun Int.rot(n: Int) = mod(n).takeIf { it != 0 } ?: n
fun Int.distanceTo(o: Int) = (this - o).absoluteValue