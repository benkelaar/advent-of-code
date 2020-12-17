package com.bartenkelaar.year2020.luggage

data class LuggageRule(val color: Color, val conditions: List<Condition>) {
    fun allows(color: Color) = color in conditions.map { it.color }

    companion object {
        fun forLine(line: String): LuggageRule? {
            val (bagDescriptor, rules) = line.split(" contain ")
            return if (rules.startsWith("no")) null else LuggageRule(Color(bagDescriptor), parseRules(rules))
        }

        private fun parseRules(rules: String) = rules.split(", ").map(Condition::forDescriptor)
    }
}

data class Color(val modifier: String, val colorId: String) {
    constructor(bagDescriptor: String) : this(bagDescriptor.trim().split(" "))
    private constructor(bagDescriptorParts: List<String>) : this(bagDescriptorParts[0], bagDescriptorParts[1])
}

data class Condition(val color: Color, val amount: Int) {
    companion object {
        fun forDescriptor(descriptor: String): Condition {
            val parts = descriptor.trim().split(" ")
            return Condition(
                color = Color(parts[1], parts[2]),
                amount = parts[0].toInt()
            )
        }
    }
}