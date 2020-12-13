package day13

import java.io.File


object Loader {

    fun load(): Pair<Passenger, List<Bus>> {
        val inputFileUrl = this::class.java.classLoader.getResource("day13.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines ->
                val asList = lines.toList()
                val passengerArrivesAt = asList[0].trim().toLong()
                val buses = asList[1].split(",")
                    .map { it.trim() }
                    .filter { it != "x" }
                    .map { Bus(it.toLong()) }
                Pair(Passenger(passengerArrivesAt), buses)
            }
    }
}

fun main() {
    val (passenger, buses) = Loader.load()
    val (closestBus, closestDeparture) = buses
        .map { bus -> bus to bus.earliestDepartureFor(passenger) }
        .minByOrNull { it.second }!!
    println("Result 1 is ${closestBus.id * (closestDeparture - passenger.arrivesAt)}")
}