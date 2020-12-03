package day3

import java.io.File

object Loader {

    fun load(): List<List<Field>> {
        val inputFileUrl = this::class.java.classLoader.getResource("day3.txt")
        return File(inputFileUrl.toURI()).useLines {
            it.map { line ->
                line.trim().toList().map { char ->
                    when (char) {
                        '.' -> Field.EMPTY
                        '#' -> Field.TREE
                        else -> throw IllegalStateException("Encountered '$char'")
                    }
                }
            }.toList()
        }
    }

}

fun main() {
    val input = Loader.load()
    val counter = TreeCounterImpl(3, 1)
    val result = counter.countTrees(Grid(input))
    println("Initially encountered $result trees")

    val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
    val multipliedEncounteredTrees = slopes
        .map { TreeCounterImpl(it.first, it.second) }
        .map { it.countTrees(Grid(input)) }
        .reduce { acc, i -> acc * i }
    println("Multiplication result is $multipliedEncounteredTrees")
}