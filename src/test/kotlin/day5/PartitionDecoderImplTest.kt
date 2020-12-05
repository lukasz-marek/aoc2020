package day5

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PartitionDecoderImplTest {

    @Test
    fun `should decode seat row`() {
        // given
        val encodedRow = listOf(
            StepDirection.Lower,
            StepDirection.Upper,
            StepDirection.Lower,
            StepDirection.Upper,
            StepDirection.Upper,
            StepDirection.Lower,
            StepDirection.Lower
        )
        val decoder = PartitionDecoderImpl(0 to 127)

        // when
        val decoded = decoder.decode(encodedRow)

        // then
        expectThat(decoded).isEqualTo(44)
    }

    @Test
    fun `should decode seat column`() {
        // given
        val encodedRow = listOf(StepDirection.Upper, StepDirection.Lower, StepDirection.Upper)
        val decoder = PartitionDecoderImpl(0 to 7)

        // when
        val decoded = decoder.decode(encodedRow)

        // then
        expectThat(decoded).isEqualTo(5)
    }
}