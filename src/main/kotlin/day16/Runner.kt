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
    println("ticket scanning error rate is $result1")

    val identifiedFields = identifyFields(input.otherTickets, input.rules)
    check(identifiedFields.size == 20)

    val indexesForDeparture = identifiedFields
        .filterKeys { it.startsWith("departure") }
        .values
        .toList()
    val result2 = indexesForDeparture
        .map { input.myTicket.numbers[it] }
        .map { it.toLong() }
        .reduce { acc, i -> acc * i }
    println("result2 is $result2")
}

