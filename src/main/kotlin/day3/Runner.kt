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

fun main(){
    val input = Loader.load()
    val counter = TreeCounterImpl()
    val result = counter.countTrees(Grid(input))
    println("Encountered $result trees")
}