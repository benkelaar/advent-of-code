package com.bartenkelaar.util

abstract class Solver {
    private var disabled = false;

    abstract fun solve(input: List<String>): Pair<Any, Any>

    fun maybeSolve(input: List<String>) = if (disabled) "set to" to "disabled" else solve(input)
    fun disabled() = apply { disabled = true }
}