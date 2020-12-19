package day19

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo


@FlowPreview
class AlternativeRuleTest {

    @Test
    fun `should accept either a or b`() {
        // given
        val tested =
            AlternativeRule(listOf(SequenceRule(listOf(TerminalRule('a'))), SequenceRule(listOf(TerminalRule('b')))))
        // when
        val matchedA = runBlocking { tested.match(listOf('a')).toList() }
        // then
        expectThat(matchedA).isEqualTo(listOf(RuleMatch(listOf('a'))))
        // when
        val matchedB = runBlocking { tested.match(listOf('b')).toList() }
        // then
        expectThat(matchedB).isEqualTo(listOf(RuleMatch(listOf('b'))))
    }

    @Test
    fun `should only accept a from ab`() {
        // given
        val tested =
            AlternativeRule(listOf(SequenceRule(listOf(TerminalRule('a'))), SequenceRule(listOf(TerminalRule('b')))))
        // when
        val matchedA = runBlocking { tested.match(listOf('a', 'b')).toList() }
        // then
        expectThat(matchedA).isEqualTo(listOf(RuleMatch(listOf('a'))))
    }
}