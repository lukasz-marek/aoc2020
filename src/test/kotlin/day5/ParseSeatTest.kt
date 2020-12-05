package day5

import day5.StepDirection.Lower
import day5.StepDirection.Upper
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ParseSeatTest {

    @Test
    fun `should successfully parse seat`() {
        // given
        val seatValue = "FBFBBFFRLR"

        // when
        val parsed = parseSeat(seatValue)

        // then
        expectThat(parsed) {
            get { row }.isEqualTo(listOf(Lower, Upper, Lower, Upper, Upper, Lower, Lower))
            get { column }.isEqualTo(listOf(Upper, Lower, Upper))
        }
    }
}