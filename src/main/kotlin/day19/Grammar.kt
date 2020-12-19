package day19


data class RuleMatch(val matched: List<Char>)

interface ProductionRule {
    fun match(input: List<Char>): List<RuleMatch>
}

data class TerminalRule(val symbol: Char) : ProductionRule {
    override fun match(input: List<Char>): List<RuleMatch> =
        if (input.first() == symbol)
            listOf(RuleMatch(listOf(input.first())))
        else emptyList()
}

data class SequenceRule(val symbolSequence: List<ProductionRule>) : ProductionRule {
    override fun match(input: List<Char>): List<RuleMatch> = match(input, symbolSequence).distinct()

    private fun match(input: List<Char>, rules: List<ProductionRule>): List<RuleMatch> {
        val currentRule = rules.first()
        val remainingRules = rules.drop(1)
        val matchedResults = mutableListOf<RuleMatch>()
        for (possibleMatch in currentRule.match(input)) {
            if (remainingRules.isEmpty()) {
                matchedResults.add(possibleMatch)
            } else {
                val remainingInput = input.drop(possibleMatch.matched.size)
                val subMatches = match(remainingInput, remainingRules)
                for (subMatch in subMatches) {
                    matchedResults.add(RuleMatch(possibleMatch.matched + subMatch.matched))
                }
            }
        }
        return matchedResults
    }
}

data class AlternativeRule(val alternatives: List<List<ProductionRule>>) : ProductionRule {
    override fun match(input: List<Char>): List<RuleMatch> =
        alternatives.flatMap { SequenceRule(it).match(input) }.distinct()
}