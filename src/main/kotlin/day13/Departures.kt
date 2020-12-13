package day13

data class Bus(val id: Long)

fun Bus.departures() = generateSequence(0L) { it + id }

data class Passenger(val arrivesAt: Long)

fun Bus.earliestDepartureFor(passenger: Passenger): Long =
    departures().first { it >= passenger.arrivesAt }