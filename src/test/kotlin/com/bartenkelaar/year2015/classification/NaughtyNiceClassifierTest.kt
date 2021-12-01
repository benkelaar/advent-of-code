package com.bartenkelaar.year2015.classification

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NaughtyNiceClassifierTest {
    private val classifier = NaughtyNiceClassifier()

    @Test
    fun `it gets the examples right`() {
        assertEquals(2 to 0, classifier.solve(listOf(
            "ugknbfddgicrmopn",
            "aaa",
            "jchzalrnumimnmhp",
            "haegwjzuvuyypxyu",
            "dvszwmarrgswjxmb"
        )))
    }
}