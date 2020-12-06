package day6

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

class IdentifyGroupsTest {
    private val input = """abc

a
b
c

ab
ac

a
a
a
a

b"""

    @Test
    fun `should successfully identify groups`() {
        // given / when
        val groups = identifyGroups(input.split("\n"))
        // then
        expectThat(groups) {
            hasSize(5)
            get(0).isEqualTo(listOf("abc"))
            get(1).isEqualTo(listOf("a", "b", "c"))
            get(2).isEqualTo(listOf("ab", "ac"))
            get(3).isEqualTo(listOf("a", "a", "a", "a"))
            get(4).isEqualTo(listOf("b"))
        }
    }
}