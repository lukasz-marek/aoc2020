package day13

data class Bus(val id: Long)

fun Bus.departures(startingPoint: Long = 0): Sequence<Long> {
    val multiplier = startingPoint / id
    val seed = multiplier * id
    return generateSequence(seed) { it + id }
}

data class Passenger(val arrivesAt: Long)

fun Bus.earliestDepartureFor(passenger: Passenger): Long =
    departures(passenger.arrivesAt).first { it >= passenger.arrivesAt }

fun interface DepartureConstraint {
    fun check(departure: Long): Boolean
}