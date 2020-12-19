package day18

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ReduceExpressionTest {
    @Test
    fun `result should be equal to expected value 1`() {
        // given
        val expression = "1 + (2 * 3) + (4 * (5 + 6))"
        val parsed = convertToReversePolishNotation(expression)
        // when
        val result = reduceExpression(parsed)
        // then
        expectThat(result).isEqualTo(51)
    }

    @Test
    fun `result should be equal to expected value 2`() {
        // given
        val expression = "2 + 2"
        val parsed = convertToReversePolishNotation(expression)
        // when
        val result = reduceExpression(parsed)
        // then
        expectThat(result).isEqualTo(4)
    }

    @Test
    fun `result should be equal to expected value 3`() {
        // given
        val expression = "7 + 5 * 3"
        val parsed = convertToReversePolishNotation(expression)
        // when
        val result = reduceExpression(parsed)
        // then
        expectThat(result).isEqualTo(36)
    }

    @Test
    fun `result should be equal to expected value 4`() {
        // given
        val expression = "7 * 5 + 3"
        val parsed = convertToReversePolishNotation(expression)
        // when
        val result = reduceExpression(parsed)
        // then
        expectThat(result).isEqualTo(38)
    }

    @Test
    fun `result should be equal to expected value 5`() {
        // given
        val expression = "1 * 2 * 3 * 4 * 5"
        val parsed = convertToReversePolishNotation(expression)
        // when
        val result = reduceExpression(parsed)
        // then
        expectThat(result).isEqualTo(120)
    }


    @Test
    fun `result should be equal to expected value 6`() {
        // given
        val expression = "2 * 3"
        val parsed = convertToReversePolishNotation(expression)
        // when
        val result = reduceExpression(parsed)
        // then
        expectThat(result).isEqualTo(6)
    }
}