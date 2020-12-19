package day19

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


data class RuleMatch(val matched: List<Char>)

interface ProductionRule {
    suspend fun match(input: List<Char>): Flow<RuleMatch>
}

data class RuleReference(val id: Int, val mapping: Map<Int, ProductionRule>) : ProductionRule {
    override suspend fun match(input: List<Char>): Flow<RuleMatch> =
        mapping[id]!!.match(input)
}

data class TerminalRule(val symbol: Char) : ProductionRule {
    override suspend fun match(input: List<Char>): Flow<RuleMatch> = flow {
        if (input.isNotEmpty() && input.first() == symbol)
            emit(RuleMatch(listOf(input.first())))
    }
}

data class SequenceRule(val symbolSequence: List<ProductionRule>) : ProductionRule {
    override suspend fun match(input: List<Char>): Flow<RuleMatch> = match(input, symbolSequence)

    private suspend fun match(input: List<Char>, rules: List<ProductionRule>): Flow<RuleMatch> {
        val currentRule = rules.first()
        val remainingRules = rules.drop(1)
        return flow {
            currentRule.match(input).collect { possibleMatch ->
                if (remainingRules.isEmpty()) {
                    emit(possibleMatch)
                } else {
                    val remainingInput = input.drop(possibleMatch.matched.size)
                    val subMatches = match(remainingInput, remainingRules)
                    emitAll(subMatches.map { RuleMatch(possibleMatch.matched + it.matched) })
                }
            }
        }
    }
}

@FlowPreview
data class AlternativeRule(val alternatives: List<ProductionRule>) : ProductionRule {
    override suspend fun match(input: List<Char>): Flow<RuleMatch> {
        return alternatives.asFlow().map { it.match(input) }.flattenConcat()
    }
}

@FlowPreview
fun parseRules(rules: List<String>): Map<Int, ProductionRule> {
    val registry = mutableMapOf<Int, ProductionRule>()
    for (line in rules) {
        val rule = parseRule(line.trim(), registry)
        registry[rule.first] = rule.second
    }
    return registry
}

private val terminalRulePattern = Regex("^(\\d+): \"(\\w)\"$")
private val nonTerminalRulePattern = Regex("^(\\d+): ((\\d+)( (\\d+))*( \\| (\\d+)( (\\d+))*)*)$")

@FlowPreview
fun parseRule(rule: String, mapping: Map<Int, ProductionRule>): Pair<Int, ProductionRule> {
    return when {
        terminalRulePattern.matches(rule) -> {
            val (ruleId, symbol) = terminalRulePattern.matchEntire(rule)!!.destructured
            Pair(ruleId.toInt(), TerminalRule(symbol.toCharArray().first()))
        }
        nonTerminalRulePattern.matches(rule) -> {
            val match = nonTerminalRulePattern.matchEntire(rule)
            val (ruleId, ruleContent) = match!!.destructured
            val alternatives = ruleContent.split("|")
                .asSequence()
                .filter { it.isNotEmpty() }
                .map { singleRule ->
                    singleRule.split(" ")
                        .asSequence()
                        .filter { it.isNotEmpty() }.map { RuleReference(it.toInt(), mapping) }
                        .toList()
                }
                .toList()
            val parsedRule = if (alternatives.size == 1) {
                SequenceRule(alternatives.first())
            } else {
                AlternativeRule(alternatives.map { SequenceRule(it) })
            }
            Pair(ruleId.toInt(), parsedRule)
        }
        else -> throw IllegalStateException("Failed to parse rule!")
    }
}

fun match(rules: Map<Int, ProductionRule>, input: String): Boolean {
    val rootRule = rules[0]!!
    val inputSequence = input.toCharArray().toList()
    return runBlocking(Dispatchers.Default) {
        rootRule.match(inputSequence).count { it.matched.size == input.length } > 0
    }
}