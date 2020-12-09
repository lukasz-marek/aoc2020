package day9

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class InvalidNumberFinderImplTest {
    private val tested = InvalidNumberFinderImpl()

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
    """.trimIndent().split("\n").map { it.toInt() }

    @Test
    fun `Should successfully identify invalid value`() {
        // given
        val encryptedData = EncryptedData(numbers = input, preambleSize = 5)
        // when
        val invalidNumbers = tested.findInvalidNumbers(encryptedData)
        // then
        expectThat(invalidNumbers).isEqualTo(listOf(127))
    }
}