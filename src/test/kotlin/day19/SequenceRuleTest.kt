package day19

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SequenceRuleTest {

    @Test
    fun `should accept ab`() {
        // given
        val tested = SequenceRule(listOf(TerminalRule('a'), TerminalRule('b')))
        val symbols = listOf('a', 'b')
        // when
        val result = tested.match(symbols)
        // then
        expectThat(result).isEqualTo(listOf(RuleMatch(listOf('a', 'b'))))
    }

    @Test
    fun `should accept abb`() {
        // given
        val tested = SequenceRule(listOf(TerminalRule('a'), TerminalRule('b')))
        val symbols = listOf('a', 'b')
        // when
        val result = tested.match(symbols)
        // then
        expectThat(result).isEqualTo(listOf(RuleMatch(listOf('a', 'b'))))
    }

    @Test
    fun `should reject aab`() {
        // given
        val tested = SequenceRule(listOf(TerminalRule('a'), TerminalRule('b')))
        val symbols = listOf('a', 'a', 'b')
        // when
        val result = tested.match(symbols)
        // then
        expectThat(result).isEqualTo(emptyList())
    }
}