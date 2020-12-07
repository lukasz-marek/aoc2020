package day7

import java.io.File

object Loader {

    fun load(): List<Rule> {
        val inputFileUrl = this::class.java.classLoader.getResource("day7.txt")
        return File(inputFileUrl.toURI())
            .useLines { it.map { rule -> parseRule(rule) }.toList() }
    }
}

fun main() {
    val rules = Loader.load()
    val inferenceRunner = InferenceRunnerImpl(rules)
    val searchedBag = Bag("shiny gold")
    val bagsContainingSearchedBag = inferenceRunner.inferPossibleContainingBags(searchedBag)
    println("There's ${bagsContainingSearchedBag.size} bags that contain $searchedBag")

    val counter = BagsCounterImpl(rules)
    val numberOfRequiredBags = counter.countBagsRequiredBy(searchedBag)
    println("Exactly $numberOfRequiredBags bags are required.")
}