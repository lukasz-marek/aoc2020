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

fun Bus.departuresWithinRange(startExclusive: Long, endInclusive: Long): Sequence<Long> {
    val actualStart = (startExclusive / id) * id
    return generateSequence(actualStart) { it + id }.dropWhile { it < startExclusive }.takeWhile { it <= endInclusive }
}

class BacktrackingSequenceFinder : SequenceFinder {
    override fun find(buses: List<DepartureConstraint>): Long {
        val sortedByPeriod = buses.sortedByDescending { it.bus.id }
        return searchForSequence(sortedByPeriod)
    }

    private fun searchForSequence(remainingConstraints: List<DepartureConstraint>): Long {
        val constraintWithGreatestPeriod = remainingConstraints.first()
        var timestampsSequence = constraintWithGreatestPeriod.bus.departuresWithinRange(1, Long.MAX_VALUE)
            .map { it - constraintWithGreatestPeriod.offset }
        for (constraint in remainingConstraints.drop(1)) {
            timestampsSequence = timestampsSequence.filter { (it + constraint.offset) % constraint.bus.id == 0L }
        }
        return timestampsSequence.first()
    }
}