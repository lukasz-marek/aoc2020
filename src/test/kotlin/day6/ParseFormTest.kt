package day6

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ParseFormTest {

    @Test
    fun `should successfully parse form`() {
        // given
        val rawForm = "abc"
        // when
        val parsed = parseForm(rawForm)
        // then
        expectThat(parsed) {
            get { declarations }.isEqualTo(setOf('a', 'b', 'c'))
        }
    }
}