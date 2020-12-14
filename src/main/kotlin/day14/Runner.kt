package day14

import java.io.File


object Loader {

    fun load(): List<InitializationInstruction> {
        val inputFileUrl = this::class.java.classLoader.getResource("day14.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { parseInstruction(it.trim()) }.toList() }
    }
}

fun main() {
    val input = Loader.load()
    val initialState = State(mask = Bitmask("X".repeat(36)), memory = emptyMap())
    val finalSum = input.fold(initialState) { state, instruction -> instruction.execute(state) }.memory.values.sum()
    println("$finalSum")
}