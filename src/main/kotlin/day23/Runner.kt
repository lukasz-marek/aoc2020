package day23

fun main() {
    val input = "156794823".split("").filter { it.isNotEmpty() }.map { Cup(it.toInt()) }.toList()
    val result = play(input, 100).map { it.value }.joinToString(separator = "")
    println(result)
}