package day23

import java.util.concurrent.TimeUnit

inline class Cup(val value: Int)

fun play(input: List<Cup>, moves: Int): List<Cup> {
    val cups = input.toMutableList()
    var currentCupIndex = 0
    val startTime = System.currentTimeMillis()
    repeat(moves) { round ->
        if ((round + 1) % 1000 == 0) {
            val currentTime = System.currentTimeMillis()
            val passed = TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime)
            println("round ${round + 1} of $moves, $passed seconds have passed since start")
        }
        val currentCup = cups[currentCupIndex]

        val takenCupsIndexes = (1..3).asSequence()
            .map { it + currentCupIndex }
            .map { it % cups.size }
            .toList()


        val cupsTaken = takenCupsIndexes.asSequence()
            .withIndex()
            .sortedByDescending { it.value }
            .map { it.index to cups.removeAt(it.value) }
            .sortedBy { it.first }
            .map { it.second }.toList()

        val destinationCupIndex = generateSequence(currentCup.value - 1) { it - 1 }
            .takeWhile { it > 0 }
            .flatMap { cups.asSequence().withIndex().filter { (_, value) -> value.value == it }.map { it.index } }
            .firstOrNull()
            ?: cups.withIndex().maxByOrNull { it.value.value }!!.index
        cups.addAll(destinationCupIndex + 1, cupsTaken)

        val offsetDueToRemoval = takenCupsIndexes.count { it < currentCupIndex }
        val offsetDueToAddition = if (destinationCupIndex >= currentCupIndex) 0 else 3
        currentCupIndex = (currentCupIndex + 1 - offsetDueToRemoval + offsetDueToAddition) % cups.size
    }
    return cups
}