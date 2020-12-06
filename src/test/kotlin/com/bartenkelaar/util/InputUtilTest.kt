package com.bartenkelaar.util

import kotlin.test.Test
import kotlin.test.assertEquals

class InputUtilTest {
    @Test
    fun `given multiple inputs split by empty lines, group them correctly`() {
        val input = listOf("a", "b", "", "c", "d", "e", "", "f")

        assertEquals(listOf(
            listOf("a", "b"),
            listOf("c", "d", "e"),
            listOf("f"),
        ), input.zipPerEmpty())
    }
}

