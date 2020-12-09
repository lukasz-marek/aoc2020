package day8

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ParseInstructionTest {
    @Test
    fun `parse nop instruction`() {
        // given
        val instruction = "nop +0"

        // when
        val parsed = parseInstruction(instruction)

        // then
        expectThat(parsed).isEqualTo(NopInstruction(0))
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 30, -30, -2, -1])
    fun `parse jmp instruction`(by: Int) {
        // given
        val instruction = "jmp $by"

        // when
        val parsed = parseInstruction(instruction)

        // then
        expectThat(parsed).isEqualTo(JmpInstruction(by))
    }


    @ParameterizedTest
    @ValueSource(ints = [1, 2, 30, -30, -2, -1])
    fun `parse acc instruction`(by: Int) {
        // given
        val instruction = "acc $by"

        // when
        val parsed = parseInstruction(instruction)

        // then
        expectThat(parsed).isEqualTo(AccInstruction(by))
    }
}
