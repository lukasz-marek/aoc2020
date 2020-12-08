package day8

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class AddInstructionTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 1234, -0, -1, -100, -235])
    fun `should increment or decrement acc by specified value`(incrementBy: Int) {
        // given
        val tested = AddInstruction(incrementBy)
        val accumulator = 100
        val state = InterpreterState(accumulator = accumulator, executedLines = emptyList(), executionIndex = 7)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { this.accumulator }.isEqualTo(accumulator + incrementBy)
            get { executedLines }.isEqualTo(listOf(ProgramLine(7, tested)))
            get { executionIndex }.isEqualTo(8)
        }
    }
}