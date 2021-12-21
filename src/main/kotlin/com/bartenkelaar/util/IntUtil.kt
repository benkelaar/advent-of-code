package com.bartenkelaar.util

fun Int.rot(n: Int) = mod(n).takeIf { it != 0 } ?: n