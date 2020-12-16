package day16

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class IdentifyFieldsTest {

    @Test
    fun `test 1`() {
        // given
        val nearbyTickets = listOf(
            Ticket(listOf(3, 9, 18)),
            Ticket(listOf(15, 1, 5)),
            Ticket(listOf(5, 14, 9))
        )
        val rules = listOf(
            ValidationRule("class", listOf(Pair(0, 1), Pair(4, 19))),
            ValidationRule("row", listOf(Pair(0, 5), Pair(8, 19))),
            ValidationRule("seat", listOf(Pair(0, 13), Pair(16, 19)))
        )
        // when
        val identifiedFields = identifyFields(nearbyTickets, rules)
        // then
        expectThat(identifiedFields).isEqualTo(mapOf("row" to 0, "class" to 1, "seat" to 2))
    }
}