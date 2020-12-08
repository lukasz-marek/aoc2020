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
    val indexesToCheck = program.possiblyCorruptedIndexes()
    val interpreter = InterpreterImpl()

    val lastInstruction = ProgramLine(program.lines.lastIndex, program.lines.last())
    val isCorrect = { state: InterpreterState -> state.executedLines.last() == lastInstruction }

    for (indexToFlip in indexesToCheck) {
        val flipped = program.lines[indexToFlip].flip()
        val newProgram = program.replaceInstruction(indexToFlip, flipped)
        val result = interpreter.run(newProgram)
        if (isCorrect(result)) {
            return result
        }
    }
    throw IllegalStateException("Failed to find correct program output!")
}

private fun Program.possiblyCorruptedIndexes(): Set<Int> = this.lines.withIndex().filter {
    when (it.value) {
        is NopInstruction -> true
        is JmpInstruction -> true
        else -> false
    }
}.map { it.index }.toSet()

private fun ExecutableInstruction.flip(): ExecutableInstruction =
    when (this) {
        is NopInstruction -> JmpInstruction(this.arg)
        is JmpInstruction -> NopInstruction(this.jumpBy)
        else -> throw IllegalArgumentException("Cannot flip $this")
    }

private fun Program.replaceInstruction(indexToReplace: Int, replacement: ExecutableInstruction): Program {
    val newProgram =
        this.lines.mapIndexed { index, instruction -> if (indexToReplace == index) replacement else instruction }
    return Program(newProgram)
}
