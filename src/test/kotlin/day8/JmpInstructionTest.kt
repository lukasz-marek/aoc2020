package day8

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class JmpInstructionTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 10, -10, -5, -1, -100])
    fun `should jump by a correct amount`(jumpBy: Int) {
        // given
        val tested = JmpInstruction(jumpBy)
        val state = InterpreterState(accumulator = 0, executedLines = emptyList(), executionIndex = 100)
        // when
        val nextState = tested.execute(state)
        // then
        expectThat(nextState) {
            get { accumulator }.isEqualTo(0)
            get { executedLines }.isEqualTo(listOf(ProgramLine(100, tested)))
            get { executionIndex }.isEqualTo(100 + jumpBy)
        }
    }

}