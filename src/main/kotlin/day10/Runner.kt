package day10

import java.io.File

object Loader {

    fun load(): List<Adapter> {
        val inputFileUrl = this::class.java.classLoader.getResource("day10.txt")
        return File(inputFileUrl.toURI())
            .useLines { it.map { line -> Adapter(line.toInt()) }.toList() }
    }
}

fun main() {
    val adapters = Loader.load()
    val connector = AdaptersConnectorImpl()
    val connectionResult = connector.connect(adapters)
    println("Result it ${connectionResult[1]!! * connectionResult[3]!!}")

    val counter = ArrangementsCounterImpl()
    println("There's ${counter.count(adapters)} arrangements possible.")
}