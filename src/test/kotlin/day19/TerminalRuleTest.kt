package day19

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TerminalRuleTest {

    @ParameterizedTest
    @ValueSource(chars = ['a', 'b', 'c', 'd'])
    fun `should accept valid symbol`(acceptedValue: Char) {
        // given
        val tested = TerminalRule(acceptedValue)
        // when
        val result = tested.match(listOf(acceptedValue))
        // then
        expectThat(result).isEqualTo(listOf(RuleMatch(listOf(acceptedValue))))
    }

    @Test
    fun `should accept valid symbol from a sequence`() {
        // given
        val tested = TerminalRule('a')
        // when
        val result = tested.match(listOf('a', 'b', 'c'))
        // then
        expectThat(result).isEqualTo(listOf(RuleMatch(listOf('a'))))
    }

    @Test
    fun `should reject invalid symbol from a sequence`() {
        // given
        val tested = TerminalRule('b')
        // when
        val result = tested.match(listOf('a', 'b', 'c'))
        // then
        expectThat(result).isEqualTo(emptyList())
    }

    @ParameterizedTest
    @ValueSource(chars = ['a', 'b', 'c', 'd'])
    fun `should reject invalid symbol`(invalidValue: Char) {
        // given
        val tested = TerminalRule('z')
        // when
        val result = tested.match(listOf(invalidValue))
        // then
        expectThat(result).isEqualTo(emptyList())
    }
}