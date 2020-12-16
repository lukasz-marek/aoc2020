package day16

data class ValidationRule(val name: String, val ranges: List<Pair<Int, Int>>)
data class Ticket(val numbers: List<Int>)

fun Ticket.invalidNumbers(rules: List<ValidationRule>): List<Int> =
    numbers.filterNot { number -> rules.any { it.isSatisfied(number) } }

fun ValidationRule.isSatisfied(value: Int) = ranges.any { it.contains(value) }

fun Ticket.isValid(rules: List<ValidationRule>): Boolean = invalidNumbers(rules).isEmpty()

fun Ticket.rulesDescribingFields(rules: List<ValidationRule>): List<Set<ValidationRule>> {
    val rulesForFields = mutableListOf<Set<ValidationRule>>()
    for (number in numbers) {
        val satisfiedRules = rules.asSequence().filter { it.isSatisfied(number) }.toSet()
        rulesForFields.add(satisfiedRules)
    }
    return rulesForFields.toList()
}

fun List<List<Set<ValidationRule>>>.flattenByIntersecting(): List<Set<ValidationRule>> =
    reduce { hypothesis, sample -> hypothesis.intersectPositionally(sample) }

private fun List<Set<ValidationRule>>.intersectPositionally(other: List<Set<ValidationRule>>): List<Set<ValidationRule>> =
    this.zip(other).map { it.first.intersect(it.second) }


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