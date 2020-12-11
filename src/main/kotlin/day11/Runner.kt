package day11

import java.io.File


object Loader {

    fun load(): Grid {
        val inputFileUrl = this::class.java.classLoader.getResource("day11.txt")
        return File(inputFileUrl.toURI())
            .useLines { parseInput(it.toList()) }
    }
}

fun main() {
    val inputGrid = Loader.load()
    val simulator = SimulatorImpl()
    val outputGrid = simulator.runSimulations(inputGrid)
    val occupiedSeatsCount = outputGrid.layout.flatten().count { it == Place.TAKEN }
    println("There's $occupiedSeatsCount occupied seats")
}
