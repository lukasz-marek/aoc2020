package day8

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class InterpreterImplTest {
    /*
    nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6
     */
    private val program = Program(
        listOf(
            NopInstruction,
            AccInstruction(1),
            JmpInstruction(4),
            AccInstruction(3),
            JmpInstruction(-3),
            AccInstruction(-99),
            AccInstruction(1),
            JmpInstruction(-4),
            AccInstruction(6)
        )
    )

    private val tested = InterpreterImpl()

    @Test
    fun `should successfully run program`() {
        // given / when
        val programResult = tested.run(program)
        // then
        expectThat(programResult).get { accumulator }.isEqualTo(5)
    }
}