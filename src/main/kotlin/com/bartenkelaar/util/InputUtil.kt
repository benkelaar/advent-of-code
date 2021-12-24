package com.bartenkelaar.util

fun Any.readFile(fileName: String, year: Int) =
    javaClass.getResource("/input/$year/$fileName").readText().lines()

fun List<String>.zipPerEmpty(): List<List<String>> = partitionPer { it.isBlank() }
    .map { group -> group.filter { it.isNotBlank() } }
    .filter { it.isNotEmpty() }

fun List<String>.nonBlank() = filter { it.isNotBlank() }