package day11

import java.io.File


object Loader {

    fun load(): Grid {
        val inputFileUrl = this::class.java.classLoader.getResource("day11.txt")
        return File(inputFileUrl.toURI())
            .useLines { parseInput(it.toList()) }
    }
}

@ExperimentalStdlibApi
fun main() {
    val inputGrid = Loader.load()
    val simulator = SimulatorImpl()
    val outputGrid = simulator.runSimulations(inputGrid)
    val occupiedSeatsCount = outputGrid.layout.flatten().count { it == Place.TAKEN }
    println("There's $occupiedSeatsCount occupied seats")

    val correctedSimulator = CorrectSimulatorImpl()
    val correctOutputGrid = correctedSimulator.runSimulations(inputGrid)
    val correctOccupiedSeatsCount = correctOutputGrid.layout.flatten().count { it == Place.TAKEN }
    println("There's $correctOccupiedSeatsCount occupied seats after correction")

}
