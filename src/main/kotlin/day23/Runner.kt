package day23

fun main() {
    val input = "156794823".split("").filter { it.isNotEmpty() }.map { Cup(it.toInt()) }.toList()
    val result = play(input, 100).map { it.value }.joinToString(separator = "")
    println(result)

    val part2Input = preparePart2Input(input)
    assert(part2Input.size == 1_000_000)
    val result2 = play(part2Input, 10000000)
    val indexOf1 = result2.indexOf(Cup(1))
    val firstIndex = (indexOf1 + 1) % result2.size
    val secondIndex = (indexOf1 + 2) % result2.size
    println("${result2[firstIndex].value * result2[secondIndex].value}")
}

private fun preparePart2Input(input: List<Cup>): List<Cup> {
    val biggestCup = input.maxByOrNull { it.value }!!
    val inputSuffix = generateSequence(biggestCup.value + 1) { it + 1 }
        .take(1000000 - input.size)
        .map { Cup(it) }
        .toList()
    return input + inputSuffix
}