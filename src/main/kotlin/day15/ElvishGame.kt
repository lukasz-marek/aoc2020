package day15

fun play(initialNumbers: List<Int>, rounds: Int): Int {
    val spokenSoFar = initialNumbers.toMutableList()

    for (round in (initialNumbers.size + 1)..rounds) {
        val previouslySpoken = spokenSoFar.last()
        val previousRound = round - 1
        val lastTimeSpoken = spokenSoFar.dropLast(1).lastIndexOf(previouslySpoken)
        val spokenNow = if (lastTimeSpoken > -1 && lastTimeSpoken < spokenSoFar.lastIndex) {
            previousRound - (lastTimeSpoken + 1)
        } else {
            0
        }
        spokenSoFar.add(spokenNow)
    }

    return spokenSoFar.last()
}

