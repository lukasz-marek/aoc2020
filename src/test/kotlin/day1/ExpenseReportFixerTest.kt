package day1

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message

class ExpenseReportFixerTest {
    private val tested = ExpenseReportFixerImpl()

    @Test
    fun `should return valid pair`() {
        // given
        val inputs = listOf(
            1721,
            979,
            366,
            299,
            675,
            1456
        )
        val report = ExpenseReport(inputs)

        // when
        val result = tested.findTwoEntriesWithSumEqualTo(report, 2020)

        // then
        expectThat(result) {
            get { first }.isEqualTo(1721)
            get { second }.isEqualTo(299)
        }
    }

    @Test
    fun `should throw exception on invalid expected`() {
        // given
        val inputs = listOf(
            1721,
            979,
            366,
            299,
            675,
            1456
        )
        val report = ExpenseReport(inputs)

        //when / then
        expectThrows<IllegalStateException> {
            tested.findTwoEntriesWithSumEqualTo(report, 2019)
        }.and { message.isEqualTo("Couldn't find entries that equal 2019") }
    }

    @Test
    fun `should return valid triplet`() {
        // given
        val inputs = listOf(
            1721,
            979,
            366,
            299,
            675,
            1456
        )
        val report = ExpenseReport(inputs)

        // when
        val result = tested.findThreeEntriesWithSumEqualTo(report, 2020)

        // then
        expectThat(result).hasSize(3)
        expectThat(result[0]).isEqualTo(979)
        expectThat(result[1]).isEqualTo(366)
        expectThat(result[2]).isEqualTo(675)
    }
}