package day25

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CalculateLoopNumberTest {

    @Test
    fun `test 1`(){
        // given
        val publicKey = 5764801L
        // when
        val loopNumber = calculateLoopNumber(publicKey)
        // then
        expectThat(loopNumber).isEqualTo(8L)
    }

    @Test
    fun `test 2`(){
        // given
        val publicKey = 17807724L
        // when
        val loopNumber = calculateLoopNumber(publicKey)
        // then
        expectThat(loopNumber).isEqualTo(11L)
    }
}