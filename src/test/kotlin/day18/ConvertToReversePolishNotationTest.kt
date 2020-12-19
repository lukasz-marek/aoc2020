package day18

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ConvertToReversePolishNotationTest {

    @Test
    fun `should succeed with a simple value`() {
        // given
        val expression = "7"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7'))
    }

    @Test
    fun `should succeed with sum of two values`() {
        // given
        val expression = "7 + 1"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7', '1', '+'))
    }

    @Test
    fun `should succeed with product of two values`() {
        // given
        val expression = "7 * 1"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7', '1', '*'))
    }

    @Test
    fun `should succeed with sum of three values`() {
        // given
        val expression = "7 + 1 + 5"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7', '1', '+', '5', '+'))
    }

    @Test
    fun `should succeed with product of three values`() {
        // given
        val expression = "7 * 1 * 5"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7', '1', '*', '5', '*'))
    }

    @Test
    fun `should succeed with expression of the form a times b plus c`() {
        // given
        val expression = "7 * 1 + 5"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7', '1', '*', '5', '+'))
    }

    @Test
    fun `should succeed with expression of the form a plus b times c`() {
        // given
        val expression = "7 + 1 * 5"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7', '1', '+', '5', '*'))
    }

    @Test
    fun `should succeed with a value in parentheses`() {
        // given
        val expression = "(5)"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('5'))
    }

    @Test
    fun `should succeed with an addition expression in parentheses`() {
        // given
        val expression = "(5 + 7)"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('5', '7', '+'))
    }

    @Test
    fun `should succeed with a multiplication expression in parentheses`() {
        // given
        val expression = "(5 * 7)"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('5', '7', '*'))
    }

    @Test
    fun `should succeed with expression of the form (a plus b) times c`() {
        // given
        val expression = "(7 + 1) * 5"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7', '1', '+', '5', '*'))
    }

    @Test
    fun `should succeed with expression of the form a plus (b times c)`() {
        // given
        val expression = "7 + (1 * 5)"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('7', '1', '5', '*', '+'))
    }

    @Test
    fun `should succeed with a complex expression 1`() {
        // given
        val expression = "8 * 1 + (5 * 7 + 2)"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('8', '1', '*', '5', '7', '*', '2', '+', '+'))
    }

    @Test
    fun `should succeed with a complex expression 2`() {
        // given
        val expression = "2 * 3 + (4 * 5)"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('2', '3', '*', '4', '5', '*', '+'))
    }

    @Test
    fun `should succeed with a complex expression 3`() {
        // given
        val expression = "5 + (8 * 3 + 9 + 3 * 4 * 3)"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(listOf('5', '8', '3', '*', '9', '+', '3', '+', '4', '*', '3', '*', '+'))
    }

    @Test
    fun `should succeed with a complex expression 4`() {
        // given
        val expression = "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
        // when
        val result = convertToReversePolishNotation(expression)
        // then
        expectThat(result).isEqualTo(
            listOf(
                '2',
                '4',
                '+',
                '9',
                '*',
                '6',
                '9',
                '+',
                '8',
                '*',
                '6',
                '+',
                '*',
                '6',
                '+',
                '2',
                '+',
                '4',
                '+',
                '2',
                '*'
            )
        )
    }
}