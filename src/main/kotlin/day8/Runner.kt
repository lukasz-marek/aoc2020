package day8

import java.io.File


object Loader {

    fun load(): List<ExecutableInstruction> {
        val inputFileUrl = this::class.java.classLoader.getResource("day8.txt")
        return File(inputFileUrl.toURI())
            .useLines { it.map { rule -> parseInstruction(rule) }.toList() }
    }
}

fun main() {
    val program = Program(Loader.load())
    val interpreter = InterpreterImpl()
    val programResult = interpreter.run(program)
    println("Acc content before infinite loop was ${programResult.accumulator}")
}