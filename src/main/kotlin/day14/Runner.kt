package day14

import java.io.File


object Loader {

    fun load(): List<InitializationInstruction> {
        val inputFileUrl = this::class.java.classLoader.getResource("day14.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { parseInstruction(it.trim()) }.toList() }
    }

    fun loadV2(): List<InitializationInstructionV2> {
        val inputFileUrl = this::class.java.classLoader.getResource("day14.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { parseInstructionV2(it.trim()) }.toList() }
    }
}

fun main() {
    val input = Loader.load()
    val initialState = State(mask = Bitmask("X".repeat(36)), memory = emptyMap())
    val finalSum = input.fold(initialState) { state, instruction -> instruction.execute(state) }.memory.values.sum()
    println("$finalSum")

    val inputV2 = Loader.loadV2()
    val initialStateV2 = StateV2(mask = BitmaskV2("X".repeat(36)), memory = emptyMap())
    val finalSumV2 =
        inputV2.fold(initialStateV2) { state, instruction -> instruction.execute(state) }.memory.values.sum()
    println("$finalSumV2")
}