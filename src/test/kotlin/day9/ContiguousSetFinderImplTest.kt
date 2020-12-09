package day9

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ContiguousSetFinderImplTest {

    private val input = """
        35
        20
        15
        25
        47
        40
        62
        55
        65
        95
        102
        117
        150
        182
        127
        219
        299
        277
        309
        576
    """.trimIndent().split("\n").map { it.toLong() }

    private val tested = ContiguousSetFinderImpl()

    @Test
    fun `should successfully find result`() {
        //given
        val sumEqualTo = 127L
        // when
        val result = tested.findContiguousSet(sumEqualTo, input)
        // then
        expectThat(result).isEqualTo(listOf(15, 25, 47, 40))
    }
}