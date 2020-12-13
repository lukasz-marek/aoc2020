package day13

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FindSequencedDepartureTest {

    @Test
    fun `test 1`() {
        // given
        val input = listOf(
            Bus(17) to DepartureConstraint { it % 17 == 0L },
            Bus(13) to DepartureConstraint { (it + 2) % 13 == 0L },
            Bus(19) to DepartureConstraint { (it + 3) % 19 == 0L })
        // when
        val result = findSequencedDeparture(input)
        // then
        expectThat(result).isEqualTo(3417)
    }

    @Test
    fun `test 2`() {
        // given
        val input = listOf(
            Bus(67) to DepartureConstraint { it % 67 == 0L },
            Bus(7) to DepartureConstraint { (it + 1) % 7 == 0L },
            Bus(59) to DepartureConstraint { (it + 2) % 59 == 0L },
            Bus(61) to DepartureConstraint { (it + 3) % 61 == 0L })

        // when
        val result = findSequencedDeparture(input)
        // then
        expectThat(result).isEqualTo(754018)
    }
}