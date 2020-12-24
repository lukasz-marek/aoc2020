package day23

inline class Cup(val value: Int)

fun play(input: List<Cup>, moves: Int): List<Cup>{
    val cups = input.toMutableList()
    var currentCup = cups.first()
    repeat(moves){
        val currentIndex = cups.indexOf(currentCup)
        val cupsTaken  = (1..3)
            .map {it + currentIndex}
            .map { it % cups.size }
            .map { cups[it] }
            .toList()
        cups.removeAll(cupsTaken)

        val destinationCup = generateSequence(currentCup.value - 1) { it - 1 }
            .takeWhile { it >= 0 }
            .firstOrNull { cups.any { cup -> cup.value == it } }
            ?.let { Cup(it) }
            ?: cups.maxByOrNull { it.value }!!
        val destinationCupIndex = cups.indexOf(destinationCup)
        cups.addAll(destinationCupIndex + 1, cupsTaken)

        val nextCurrentCupIndex = (cups.indexOf(currentCup) + 1) % cups.size
        currentCup = cups[nextCurrentCupIndex]
    }
    return cups
}