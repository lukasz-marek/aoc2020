package day8

interface ExecutableInstruction {
    fun execute(state: InterpreterState): InterpreterState
}

object NopInstruction : ExecutableInstruction {

    override fun execute(state: InterpreterState): InterpreterState {
        val currentLine = ProgramLine(state.executionIndex, this)
        return state.copy(executionIndex = state.executionIndex + 1, executedLines = state.executedLines + currentLine)
    }

}

data class AccInstruction(val amount: Int) : ExecutableInstruction {

    override fun execute(state: InterpreterState): InterpreterState {
        val currentLine = ProgramLine(state.executionIndex, this)
        return state.copy(
            accumulator = state.accumulator + amount,
            executionIndex = state.executionIndex + 1,
            executedLines = state.executedLines + currentLine
        )
    }

}

data class JmpInstruction(val jumpBy: Int) : ExecutableInstruction {

    override fun execute(state: InterpreterState): InterpreterState {
        val currentLine = ProgramLine(state.executionIndex, this)
        return state.copy(
            accumulator = state.accumulator,
            executionIndex = state.executionIndex + jumpBy,
            executedLines = state.executedLines + currentLine
        )
    }

}

data class ProgramLine(val index: Int, val instruction: ExecutableInstruction)
data class InterpreterState(val accumulator: Int, val executedLines: List<ProgramLine>, val executionIndex: Int)
data class Program(val lines: List<ExecutableInstruction>)

interface Interpreter {
    fun run(program: Program): InterpreterState
}

class InterpreterImpl : Interpreter {

    override fun run(program: Program): InterpreterState {
        val instructions = program.lines.mapIndexed { index, instruction -> ProgramLine(index, instruction) }
        val initialState = InterpreterState(accumulator = 0, executionIndex = 0, executedLines = emptyList())
        return execute(instructions, initialState)
    }

    private tailrec fun execute(instructions: List<ProgramLine>, state: InterpreterState): InterpreterState {
        val currentLine = instructions[state.executionIndex]!!
        return if (state.executedLines.contains(currentLine)) {
            state
        } else {
            val nextState = currentLine.instruction.execute(state)
            execute(instructions, nextState)
        }
    }

}

private val instructionPattern = Regex("^(.+) (.+)$")
fun parseInstruction(line: String): ExecutableInstruction {
    val match = instructionPattern.matchEntire(line.trim())!!
    val (instructionName, argument) = match.destructured
    return when (instructionName) {
        "acc" -> AccInstruction(argument.toInt())
        "nop" -> NopInstruction
        "jmp" -> JmpInstruction(argument.toInt())
        else -> throw IllegalArgumentException("'$line' is not a valid program line!")
    }
}