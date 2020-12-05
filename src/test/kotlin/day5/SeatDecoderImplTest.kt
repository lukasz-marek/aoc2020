package day5

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SeatDecoderImplTest {
    private val rowDecoder = mockk<PartitionDecoder>()
    private val columnDecoder = mockk<PartitionDecoder>()

    private val tested = SeatDecoderImpl(rowDecoder, columnDecoder)

    @Test
    fun `should successfully decode seat`() {
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
        val encodedColumn = listOf(StepDirection.Upper, StepDirection.Lower, StepDirection.Upper)
        every { rowDecoder.decode(eq(encodedRow)) } returns (44)
        every { columnDecoder.decode(eq(encodedColumn)) } returns (5)
        val encodedSeat = EncodedSeat(encodedRow, encodedColumn)

        // when
        val decoded = tested.decode(encodedSeat)

        // then
        expectThat(decoded) {
            get { row }.isEqualTo(44)
            get { column }.isEqualTo(5)
            get { seatId }.isEqualTo(357)
        }
    }
}