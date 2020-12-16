package day16

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ParseRuleTest {
    @Test
    fun `test 1`() {
        // given
        val rule = "arrival station: 49-353 or 360-967"
        // when
        val parsed = parseRule(rule)
        // then
        expectThat(parsed) {
            get { name }.isEqualTo("arrival station")
            get { ranges }.isEqualTo(listOf(Pair(49, 353), Pair(360, 967)))
        }
    }
}