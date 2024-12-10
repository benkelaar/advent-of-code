package com.bartenkelaar.year2024.operation

import kotlin.test.Test
import kotlin.test.assertEquals

class DiskDefragmenterTest {
    @Test
    fun `simple operation`() {
        val (result, _) = DiskDefragmenter().solve(listOf("12345"))

        assertEquals(60L, result)
    }

    @Test
    fun `less simple operation`() {
        val (_, result) = DiskDefragmenter().solve(listOf("2333133121414131402"))

        assertEquals(2858L, result)
    }
}
