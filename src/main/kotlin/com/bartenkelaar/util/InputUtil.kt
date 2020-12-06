package com.bartenkelaar.util



fun Any.readFile(fileName: String, year: Int) =
    javaClass.getResource("/input/$year/$fileName").readText().lines()

fun List<String>.zipPerEmpty(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    var collector = mutableListOf<String>()
    for (str in this) {
        if (str.isNullOrBlank()) {
            result += collector.toList()
            collector = mutableListOf()
        } else {
            collector.add(str)
        }
    }
    if (collector.isNotEmpty()) result += collector.toList()
    return result.toList()
}