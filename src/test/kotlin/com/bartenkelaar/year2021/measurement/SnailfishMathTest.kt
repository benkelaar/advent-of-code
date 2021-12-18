package com.bartenkelaar.year2021.measurement

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SnailfishMathTest {
    val math = SnailfishMath()

    @Test
    fun `example1`() {
        val input = listOf("[1,2]", "[[3,4],5]")
        assertEquals(143L, math.solve(input).first)
    }

    @Test
    fun `example2`() {
        val input = listOf("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]")
        assertEquals(470L, math.solve(input).first)
    }

    @Test
    fun `exampleBig1`() {
        val input = listOf(
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
            "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
            "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
            "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
            "[7,[5,[[3,8],[1,4]]]]",
            "[[2,[2,2]],[8,[8,1]]]",
            "[2,9]",
            "[1,[[[9,3],9],[[9,0],[0,7]]]]",
            "[[[5,[7,4]],7],1]",
            "[[[[4,2],2],6],[8,7]]",
        )
        val result = math.solve(input)

        assertEquals(4140, result.first)
    }

    @Test
    fun `exampleBig`() {
        val input = listOf(
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        )
        val result = math.solve(input)

        assertEquals(4140, result.first)
    }
}