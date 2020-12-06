package day6

data class DeclarationForm(val declarations: Set<Char>)

data class CustomsDeclarationsGroup(val forms: List<DeclarationForm>)

fun parseForm(form: String): DeclarationForm {
    val declarations = form.toList().filter { !it.isWhitespace() }.toSet()
    return DeclarationForm(declarations)
}

fun identifyGroups(lines: List<String>): List<List<String>> {
    val groups = mutableListOf<List<String>>()
    val groupInProgress = mutableListOf<String>()

    for (line in lines) {
        if (line.isEmpty()) {
            val group = groupInProgress.toList()
            groups.add(group)
            groupInProgress.clear()
        } else {
            groupInProgress.add(line.trim())
        }
    }
    groups.add(groupInProgress.toList())
    return groups.toList()
}

enum class AggregationMode {
    Union, Intersection
}

fun CustomsDeclarationsGroup.aggregate(aggregationMode: AggregationMode): DeclarationForm {
    val aggregated = when (aggregationMode) {
        AggregationMode.Union -> forms.flatMap { it.declarations }.toSet()
        AggregationMode.Intersection -> forms.map { it.declarations }.reduce { acc, set -> acc.intersect(set) }
    }
    return DeclarationForm(aggregated)
}