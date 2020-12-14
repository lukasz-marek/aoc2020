package day14

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BitmaskTest {

    @Test
    fun `test 1`(){
        // given
        val mask = Bitmask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        val testedValue = 11L
        // when
        val result = mask(testedValue)
        // then
        expectThat(result).isEqualTo(73L)
    }

    @Test
    fun `test 2`(){
        // given
        val mask = Bitmask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        val testedValue = 101L
        // when
        val result = mask(testedValue)
        // then
        expectThat(result).isEqualTo(101L)
    }

    @Test
    fun `test 3`(){
        // given
        val mask = Bitmask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        val testedValue = 0L
        // when
        val result = mask(testedValue)
        // then
        expectThat(result).isEqualTo(64L)
    }
}