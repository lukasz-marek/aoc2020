package day16

data class ValidationRule(val name: String, val ranges: List<Pair<Int, Int>>)
data class Ticket(val numbers: List<Int>)

fun Ticket.invalidNumbers(rules: List<ValidationRule>): List<Int> =
    numbers.filterNot { number ->
        rules.any { rule ->
            rule.ranges.any { range ->
                range.contains(number)
            }
        }
    }

private fun Pair<Int, Int>.contains(value: Int): Boolean = value in first..second

private val rulePattern = Regex("^(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$")
fun parseRule(rule: String): ValidationRule {
    val parsed = rulePattern.matchEntire(rule.trim())
    val (ruleName, range1Start, range1End, range2Start, range2End) = parsed!!.destructured
    return ValidationRule(
        ruleName,
        listOf(Pair(range1Start.toInt(), range1End.toInt()), Pair(range2Start.toInt(), range2End.toInt()))
    )
}

fun parseTicket(ticket: String): Ticket {
    val numbers = ticket.split(",").map { it.trim().toInt() }
    return Ticket(numbers)
}