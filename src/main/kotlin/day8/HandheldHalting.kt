package day8

interface ExecutableInstruction {
    fun execute(state: InterpreterState): InterpreterState
}

class NopInstruction : ExecutableInstruction {

    override fun execute(state: InterpreterState): InterpreterState {
        val currentLine = ProgramLine(state.executionIndex, this)
        return state.copy(executionIndex = state.executionIndex + 1, executedLines = state.executedLines + currentLine)
    }

}

class AddInstruction(private val amount: Int) : ExecutableInstruction {

    override fun execute(state: InterpreterState): InterpreterState {
        val currentLine = ProgramLine(state.executionIndex, this)
        return state.copy(
            accumulator = state.accumulator + amount,
            executionIndex = state.executionIndex + 1,
            executedLines = state.executedLines + currentLine
        )
    }

}

class JmpInstruction(private val jumpBy: Int) : ExecutableInstruction {


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