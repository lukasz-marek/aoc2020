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

data class DepartureConstraint(val bus: Bus, val offset: Long)

fun DepartureConstraint.check(departure: Long): Boolean = (offset + departure) % bus.id == 0L

interface SequenceFinder {
    fun find(buses: List<DepartureConstraint>): Long
}

class SequenceFinderImpl : SequenceFinder {
    override fun find(buses: List<DepartureConstraint>): Long {
        var sequenceStarter = Passenger(1)
        var resultFound: Boolean
        do {
            resultFound = true
            var lastValid = sequenceStarter.arrivesAt
            inner@ for (bus in buses) {
                val sequenceElement = bus.bus.earliestDepartureFor(sequenceStarter)
                if (sequenceElement - sequenceStarter.arrivesAt != bus.offset) {
                    resultFound = false
                    val nextTimestamp = if (lastValid == sequenceStarter.arrivesAt) lastValid + 1 else lastValid
                    sequenceStarter = Passenger(nextTimestamp)
                    break@inner
                }
                lastValid = sequenceElement
            }
        } while (!resultFound)
        return sequenceStarter.arrivesAt
    }
}