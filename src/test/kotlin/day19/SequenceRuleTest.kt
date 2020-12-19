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

    @Test
    fun `complex rule test 1`() {
        /**
         * 0: 1 2
        1: "a"
        2: 1 3 | 3 1
        3: "b"
         */
        // given
        val tested = SequenceRule(
            listOf(
                TerminalRule('a'),
                AlternativeRule(
                    listOf(
                        listOf(TerminalRule('a'), TerminalRule('b')),
                        listOf(TerminalRule('b'), TerminalRule('a'))
                    )
                )
            )
        )
        val fistSequence = listOf('a', 'a', 'b')
        val secondSequence = listOf('a', 'b', 'a')
        // when
        val result1 = tested.match(fistSequence)
        // then
        expectThat(result1).isEqualTo(listOf(RuleMatch(listOf('a', 'a', 'b'))))
        // when
        val result2 = tested.match(secondSequence)
        // then
        expectThat(result2).isEqualTo(listOf(RuleMatch(listOf('a', 'b', 'a'))))
    }
}