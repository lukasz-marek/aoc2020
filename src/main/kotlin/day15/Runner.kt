package day15

fun main() {
    val initialNumbers = listOf(13, 16, 0, 12, 15, 1)
    val rounds = 2020
    println("${rounds}th number is ${play(initialNumbers, rounds)}")
}