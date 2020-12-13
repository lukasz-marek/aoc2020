package day13

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DeparturesTest {

    @Test
    fun `should generate valid sequence of departures`() {
        // given
        val bus = Bus(7)
        // when
        val departures = bus.departures()
        // then
        expectThat(departures.take(100).toList()).isEqualTo(generateSequence(0L) { it + 7 }.take(100).toList())
    }

    @Test
    fun `should find earliest departure 1`() {
        // given
        val bus = Bus(7)
        val passenger = Passenger(4 * 7 + 1)
        // when
        val earliestDeparture = bus.earliestDepartureFor(passenger)
        // then
        expectThat(earliestDeparture).isEqualTo(5 * 7)
    }

    @Test
    fun `should find earliest departure 2`() {
        // given
        val bus = Bus(7)
        val passenger = Passenger(4 * 7)
        // when
        val earliestDeparture = bus.earliestDepartureFor(passenger)
        // then
        expectThat(earliestDeparture).isEqualTo(4 * 7)
    }
}