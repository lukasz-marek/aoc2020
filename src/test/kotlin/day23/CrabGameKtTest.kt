package day23

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CrabGameKtTest {

    @Test
    fun `test 1`() {
        val input = parseInput("156794823")
        val output = play(input, 100)
        expectThat(output.map { it.value }.joinToString(separator = "")).isEqualTo("257349618")
    }

    @Test
    fun `test 2`() {
        val input = parseInput("389125467")
        val output = play(input, 100)
        expectThat(output.map { it.value }.joinToString(separator = "")).isEqualTo("291673845")
    }


    private fun parseInput(input: String): List<Cup> =
        input.split("").filter { it.isNotEmpty() }.map { Cup(it.toInt()) }.toList()
}