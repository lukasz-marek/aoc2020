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

    val correctOutput = findCorrectProgramOutput(program)
    println("Acc output after the program terminates is ${correctOutput.accumulator}")
}

fun findCorrectProgramOutput(program: Program): InterpreterState {
    val indexesToCheck = program.lines.withIndex().filter {
        when (it.value) {
            is NopInstruction -> true
            is JmpInstruction -> true
            else -> false
        }
    }.map { it.index }
    val interpreter = InterpreterImpl()
    val lastInstruction = ProgramLine(program.lines.lastIndex, program.lines.last())
    for (indexToFlip in indexesToCheck) {
        val flipped = flipInstruction(program.lines[indexToFlip]!!)
        val newProgram =
            program.lines.mapIndexed { index, instruction -> if (index == indexToFlip) flipped else instruction }
        val result = interpreter.run(Program(newProgram))
        if (result.executedLines.last() == lastInstruction) {
            return result
        }
    }
    throw IllegalStateException("Failed to find correct program output!")
}

private fun flipInstruction(instruction: ExecutableInstruction): ExecutableInstruction =
    when (instruction) {
        is NopInstruction -> JmpInstruction(instruction.arg)
        is JmpInstruction -> NopInstruction(instruction.jumpBy)
        else -> throw IllegalArgumentException("Cannot flip $instruction")
    }
