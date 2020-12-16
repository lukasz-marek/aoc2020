package day16

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TicketInvalidNumbersTest {

    @Test
    fun `test 1`() {
        // given
        val nearbyTickets = listOf(
            Ticket(listOf(7, 3, 47)),
            Ticket(listOf(40, 4, 50)),
            Ticket(listOf(55, 2, 20)),
            Ticket(listOf(38, 6, 12))
        )
        val rules = listOf(
            ValidationRule("class", listOf(Pair(1, 3), Pair(5, 7))),
            ValidationRule("row", listOf(Pair(6, 11), Pair(33, 44))),
            ValidationRule("seat", listOf(Pair(13, 40), Pair(45, 50)))
        )
        // when
        val invalidNumbers = nearbyTickets.flatMap { it.invalidNumbers(rules) }
        // then
        expectThat(invalidNumbers).isEqualTo(listOf(4, 55, 12))
    }
}
