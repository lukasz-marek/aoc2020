package day13

import java.io.File


object Loader {

    fun load(): Pair<Passenger, List<Pair<Bus, DepartureConstraint>>> {
        val inputFileUrl = this::class.java.classLoader.getResource("day13.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines ->
                val asList = lines.toList()
                val passengerArrivesAt = asList[0].trim().toLong()
                val buses = asList[1].split(",")
                    .map { it.trim() }
                    .withIndex()
                    .filter { it.value != "x" }
                    .map { (index, busId) ->
                        val busIdValue = busId.toLong()
                        Bus(busIdValue) to DepartureConstraint { (it + index) % busIdValue == 0L }
                    }
                Pair(Passenger(passengerArrivesAt), buses)
            }
    }
}

fun main() {
    val (passenger, buses) = Loader.load()
    val (closestBus, closestDeparture) = buses
        .map { (bus, _) -> bus to bus.earliestDepartureFor(passenger) }
        .minByOrNull { it.second }!!
    println("Result 1 is ${closestBus.id * (closestDeparture - passenger.arrivesAt)}")

    val result2 = findSequencedDeparture(buses, 100000000000000L)
    println("Result 2 is $result2")
}

fun findSequencedDeparture(buses: List<Pair<Bus, DepartureConstraint>>, startingPoint: Long = 1): Long {
    val accumulatedConstraint =
        buses.map { it.second }.fold(emptyList<DepartureConstraint>()) { accumulatedConstraints, newConstraint ->
            accumulatedConstraints + newConstraint
        }.let { constraints -> DepartureConstraint { departure -> constraints.all { it.check(departure) } } }

    val earliestPossibleDeparture = buses.first().first.earliestDepartureFor(Passenger(startingPoint))
    val step = buses.first().first.id
    return generateSequence(earliestPossibleDeparture) { it + step }
        .first { accumulatedConstraint.check(it) }
}