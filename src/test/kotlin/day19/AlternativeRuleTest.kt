package day19

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo


class AlternativeRuleTest {

    @Test
    fun `should accept either a or b`() {
        // given
        val tested =
            AlternativeRule(listOf(SequenceRule(listOf(TerminalRule('a'))), SequenceRule(listOf(TerminalRule('b')))))
        // when
        val matchedA = tested.match(listOf('a'))
        // then
        expectThat(matchedA).isEqualTo(listOf(RuleMatch(listOf('a'))))
        // when
        val matchedB = tested.match(listOf('b'))
        // then
        expectThat(matchedB).isEqualTo(listOf(RuleMatch(listOf('b'))))
    }

    @Test
    fun `should only accept a from ab`() {
        // given
        val tested =
            AlternativeRule(listOf(SequenceRule(listOf(TerminalRule('a'))), SequenceRule(listOf(TerminalRule('b')))))
        // when
        val matchedA = tested.match(listOf('a', 'b'))
        // then
        expectThat(matchedA).isEqualTo(listOf(RuleMatch(listOf('a'))))
    }
}