package day14

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.hasSize


class BitmaskV2Test {

    @Test
    fun `test 1`() {
        // given
        val mask = BitmaskV2("000000000000000000000000000000X1001X")
        val address = 42L
        // when
        val result = mask.invoke(address).toList()
        // then
        expectThat(result) {
            hasSize(4)
            contains(26)
            contains(27)
            contains(58)
            contains(59)
        }
    }

    @Test
    fun `test 2`() {
        // given
        val mask = BitmaskV2("00000000000000000000000000000000X0XX")
        val address = 26L
        // when
        val result = mask.invoke(address).toList()
        // then
        expectThat(result) {
            hasSize(8)
            contains(16)
            contains(17)
            contains(18)
            contains(19)
            contains(24)
            contains(25)
            contains(26)
            contains(27)
        }
    }
}