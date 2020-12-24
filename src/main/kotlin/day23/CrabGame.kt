package day23

inline class Cup(val value: Int)

fun play(input: List<Cup>, moves: Int): List<Cup> {
    val cups = input.toMutableList()
    var currentCupIndex = 0
    repeat(moves) {
        val currentCup = cups[currentCupIndex]
        val cupsTaken = (1..3)
            .asSequence()
            .map { it + currentCupIndex }
            .map { it % cups.size }
            .map { it to cups[it] }
            .withIndex()
            .sortedByDescending { it.value.first }
            .onEach { cups.removeAt(it.value.first) }
            .sortedBy { it.index }
            .map { it.value.second }
            .toList()

        val destinationCup = generateSequence(currentCup.value - 1) { it - 1 }
            .takeWhile { it >= 0 }
            .firstOrNull { cups.any { cup -> cup.value == it } }
            ?.let { Cup(it) }
            ?: cups.maxByOrNull { it.value }!!
        val destinationCupIndex = cups.indexOf(destinationCup)
        cups.addAll(destinationCupIndex + 1, cupsTaken)

        currentCupIndex = (cups.indexOf(currentCup) + 1) % cups.size
    }
    return cups
}