package com.bartenkelaar.util

fun Any.readFile(fileName: String, year: Int) =
    javaClass.getResource("/input/$year/$fileName").readText().lines()

fun List<String>.zipPerEmpty(): List<List<String>> =
    (listOf(-1) + mapIndexedNotNull { i, line -> i.takeIf { line.isBlank() } } + size)
        .zipWithNext()
        .map { (f, t) -> subList(f + 1, t) }
        .filter { it.isNotEmpty() }

fun List<String>.nonBlank() = filter { it.isNotBlank() }