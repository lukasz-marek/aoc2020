package day15

fun play(initialNumbers: List<Int>, rounds: Int): Int {
    val numberToLastSpoken = mutableMapOf<Int, Int>()
    for ((index, number) in initialNumbers.withIndex().toList().dropLast(1)) {
        numberToLastSpoken[number] = index + 1
    }

    var previouslySpoken = initialNumbers.last()
    for (round in (initialNumbers.size + 1)..rounds) {
        val previousRound = round - 1
        val lastTimeSpoken = numberToLastSpoken[previouslySpoken]
        val spokenNow = lastTimeSpoken?.let { previousRound - it } ?: 0
        numberToLastSpoken[previouslySpoken] = previousRound
        previouslySpoken = spokenNow
    }

    return previouslySpoken
}

