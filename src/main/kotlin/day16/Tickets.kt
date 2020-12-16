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