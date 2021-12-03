package com.bartenkelaar.util

fun String.isPositiveNumber() = all { it.isDigit() }

fun Boolean.toBit() = if (this) '1' else '0'

fun Boolean.toBitString() = toBit().toString()