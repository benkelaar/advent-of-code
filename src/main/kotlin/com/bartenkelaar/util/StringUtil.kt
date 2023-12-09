package com.bartenkelaar.util

private val whitespaceRegex = """\s+""".toRegex()

fun String.isPositiveNumber() = all { it.isDigit() }

fun String.lastInt() = lastWord().toInt()

fun String.splitWhiteSpace() = split(whitespaceRegex)

fun String.lastWord() = splitWhiteSpace().last()

fun String.toLongList(separator: String = " ") = split(separator).map { it.toLong() }

fun String.toIntList(separator: String = " ") = split(separator).map { it.toInt() }
