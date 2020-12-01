package day1

import java.io.File

object Loader {
    fun load(): List<Int> {
        val inputFileUrl = this::class.java.classLoader.getResource("day1.txt")
        return File(inputFileUrl.toURI()).useLines { it.filter { it.isNotBlank() }.map { it.toInt() }.toList() }
    }
}

fun main() {
    val entries = Loader.load()

    val report = ExpenseReport(entries)
    val fixer = ExpenseReportFixerImpl()

    val resultPair = fixer.findTwoEntriesWithSumEqualTo(report, 2020)
    println("Pair = ${resultPair.first * resultPair.second}")

    val resultTriple = fixer.findThreeEntriesWithSumEqualTo(report, 2020)
    println("Triple = ${resultTriple.reduce { acc, i -> acc * i }}")
}