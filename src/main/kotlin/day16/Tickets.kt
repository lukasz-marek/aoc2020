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

fun identifyFields(tickets: List<Ticket>, rules: List<ValidationRule>): Map<String, Int> {
        val hypotheses = tickets.filter { it.isValid(rules) }
            .map { it.rulesDescribingFields(rules) }
            .flattenByIntersecting()
            .map { set -> set.map { it.name }.toSet() }

    return inferFields(hypotheses)
}

private fun inferFields(knowledge: List<Set<String>>): Map<String, Int> {
    val sorted = knowledge.sortedBy { it.size }.map { set -> set.toMutableSet() }
    val alreadySeen = sorted.first().toMutableSet()

    for (hypothesis in sorted.drop(1)) {
        hypothesis.removeAll(alreadySeen)
        alreadySeen.addAll(hypothesis)
        check(hypothesis.size == 1) { "Ambiguous hypothesis $hypothesis" }
    }

    return sorted.withIndex()
        .map { (index, value) -> value.map { it to index } }
        .flatten()
        .toMap()
}