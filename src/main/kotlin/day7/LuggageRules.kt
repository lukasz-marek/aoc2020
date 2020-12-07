package day7

data class Bag(val color: String)
data class Rule(val describedBag: Bag, val containedBags: Map<Bag, Int>)

private val nonEmptyRulePattern =
    Regex("^(\\w+ \\w+) bags contain (.+)\\.")
private val emptyRulePattern = Regex("^(\\w+ \\w+) bags contain no other bags.")
fun parseRule(line: String): Rule =
    when {
        emptyRulePattern.matches(line) -> {
            val (color, _) = emptyRulePattern.matchEntire(line)!!.destructured
            Rule(Bag(color), emptyMap())
        }
        nonEmptyRulePattern.matches(line) -> {
            val (color, otherBags) = nonEmptyRulePattern.matchEntire(line)!!.destructured
            val containedBags = otherBags.split(",").map { it.trim() }.map { parseContainedBag(it) }.toMap()
            Rule(Bag(color), containedBags)
        }
        else -> throw IllegalArgumentException("'$line' cannot be parsed!")
    }

private val singleBagPattern = Regex("^(\\d+) (\\w+ \\w+) bags?$")
private fun parseContainedBag(containedBag: String): Pair<Bag, Int> {
    val matched = singleBagPattern.matchEntire(containedBag)!!
    val (quantity, color) = matched.destructured
    return Bag(color) to quantity.toInt()
}

interface InferenceRunner {
    fun inferPossibleContainingBags(bag: Bag): Set<Bag>
}

class InferenceRunnerImpl(private val rules: List<Rule>) : InferenceRunner {
    override fun inferPossibleContainingBags(bag: Bag): Set<Bag> = runInference(setOf(bag), emptySet()) - setOf(bag)

    private tailrec fun runInference(searchedBags: Set<Bag>, foundBags: Set<Bag>): Set<Bag> =
        if (searchedBags.isEmpty()) {
            foundBags
        } else {
            val bagsContainingSearchedBags = rules
                .filter { it.containedBags.keys.intersect(searchedBags).isNotEmpty() }
                .map { it.describedBag }
                .toSet()
            runInference(bagsContainingSearchedBags, foundBags + searchedBags)
        }

}