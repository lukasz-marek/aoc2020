package day19

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ParseRuleTest {

    @Test
    fun `should parse terminal rule`() {
        // given
        val terminalRule = "4: \"a\""
        // when
        val parsed = parseRule(terminalRule, emptyMap())
        // then
        expectThat(parsed).isEqualTo(Pair(4, TerminalRule('a')))
    }

    @Test
    fun `should parse simple sequence rule`() {
        // given
        val terminalRule = "4: 5 6"
        // when
        val parsed = parseRule(terminalRule, emptyMap())
        // then
        expectThat(parsed).isEqualTo(
            Pair(
                4, SequenceRule(
                    listOf(
                        RuleReference(5, emptyMap()),
                        RuleReference(6, emptyMap())
                    )
                )
            )
        )
    }

    @Test
    fun `should parse simple alternative rule`() {
        // given
        val terminalRule = "4: 5 | 6"
        // when
        val parsed = parseRule(terminalRule, emptyMap())
        // then
        expectThat(parsed).isEqualTo(
            Pair(
                4, AlternativeRule(
                    listOf(
                        listOf(RuleReference(5, emptyMap())),
                        listOf(RuleReference(6, emptyMap()))
                    )
                )
            )
        )
    }
}