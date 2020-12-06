package day6

import java.io.File

object Loader {

    fun load(): List<CustomsDeclarationsGroup> {
        val inputFileUrl = this::class.java.classLoader.getResource("day6.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines ->
                val groups = identifyGroups(lines.toList())
                groups.map { group -> group.map { parseForm(it) } }
            }.map { CustomsDeclarationsGroup(it) }
    }
}

fun main() {
    val customsGroups = Loader.load()
    val sumOfQuestionsWithAffirmativeResponseFromAnyone =
        customsGroups.map { it.aggregate(AggregationMode.Union) }.map { it.declarations.size }.sum()
    println("$sumOfQuestionsWithAffirmativeResponseFromAnyone have been answered with 'Yes' by anyone")

    val sumOfQuestionsWithAffirmativeResponse =
        customsGroups.map { it.aggregate(AggregationMode.Intersection) }.map { it.declarations.size }.sum()
    println("$sumOfQuestionsWithAffirmativeResponse have been answered with 'Yes' by everyone")
}