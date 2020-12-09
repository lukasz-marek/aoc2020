package day8

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class NopInstructionTest {
    private val tested = NopInstruction(0)

    @Test
    fun `should do nothing apart from moving to the next line`() {
        // given
        val state = InterpreterState(accumulator = -1, executedLines = emptyList(), executionIndex = 15)

        // when
        val nextState = tested.execute(state)

        // then
        expectThat(nextState)
            .isEqualTo(
                InterpreterState(
                    accumulator = -1,
                    executedLines = listOf(ProgramLine(15, tested)),
                    executionIndex = 16
                )
            )
    }
}