package day9

import java.io.File

object Loader {

    fun load(): List<Long> {
        val inputFileUrl = this::class.java.classLoader.getResource("day9.txt")
        return File(inputFileUrl.toURI())
            .useLines { it.map { line -> line.toLong() }.toList() }
    }
}

fun main() {
    val input = Loader.load()
    val encryptedData = EncryptedData(input, 25)
    val invalidNumberFinder = InvalidNumberFinderImpl()
    val invalidNumbers = invalidNumberFinder.findInvalidNumbers(encryptedData)
    println("First invalid number is ${invalidNumbers.firstOrNull()}")

    val contiguousSetFinder = ContiguousSetFinderImpl()
    val set = contiguousSetFinder.findContiguousSet(invalidNumbers.first(), encryptedData.numbers)
    val smallest = set.minOrNull()!!
    val largest = set.maxOrNull()!!
    val weakness = smallest + largest
    println("Weakness is $weakness")
}
