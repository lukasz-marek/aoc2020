package day18

import java.io.File


object Loader {

    fun load(): List<String> {
        val inputFileUrl = this::class.java.classLoader.getResource("day18.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { it.trim() }.toList() }

    }
}

fun main() {
    val expressions = Loader.load()
    val result1 = expressions.map { reduceExpression(convertToReversePolishNotation(it, firstPrecedenceTable)) }.sum()
    println("$result1")

    val result2 = expressions.map { reduceExpression(convertToReversePolishNotation(it, secondPrecedenceTable)) }.sum()
    println("$result2")
}