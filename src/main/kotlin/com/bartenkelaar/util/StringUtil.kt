package com.bartenkelaar.util

fun String.isPositiveNumber() = all { it.isDigit() }

fun String.lastInt() = lastWord().toInt()

fun String.lastWord() = split(" ").last()

fun String.toLongList(separator: String = " ") = split(separator).map { it.toLong() }
