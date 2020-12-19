package day19

import java.io.File

object Loader {

    fun loadInput(): List<String> {
        val inputFileUrl = this::class.java.classLoader.getResource("day19_input.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { it.trim() }.toList() }
    }

    fun loadRules1(): Map<Int, ProductionRule> {
        val inputFileUrl = this::class.java.classLoader.getResource("day19_rules1.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> parseRules(lines.toList()) }
    }

    fun loadRules2(): Map<Int, ProductionRule> {
        val inputFileUrl = this::class.java.classLoader.getResource("day19_rules2.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> parseRules(lines.toList()) }
    }
}

fun main() {
    val rules1 = Loader.loadRules1()
    val input = Loader.loadInput()
    val matches1 = input.count { match(rules1, it) }
    println(matches1)

    val rules2 = Loader.loadRules2()
    val matches2 = input.count { match(rules2, it) }
    println(matches2)
}