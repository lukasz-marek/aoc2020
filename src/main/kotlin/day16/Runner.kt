package day16

import java.io.File

data class Input(val rules: List<ValidationRule>, val myTicket: Ticket, val otherTickets: List<Ticket>)

object Loader {

    fun load(): Input {
        val inputFileUrl = this::class.java.classLoader.getResource("day16.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines ->
                val inputLines = lines.toList()
                val rules = inputLines.takeWhile { line -> line.isNotEmpty() }.map { parseRule(it) }.toList()
                val myTicket = parseTicket(inputLines[rules.size + 2])
                val otherTickets = inputLines.drop(rules.size + 5).map { parseTicket(it) }.toList()
                Input(rules, myTicket, otherTickets)
            }
    }
}

fun main() {
    val input = Loader.load()
    val result1 = with(input) {
        otherTickets.flatMap { it.invalidNumbers(rules) }.sum()
    }
    print("ticket scanning error rate is $result1")
}