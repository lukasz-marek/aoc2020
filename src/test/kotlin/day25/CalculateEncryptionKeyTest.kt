package day25

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CalculateEncryptionKeyTest {

    @Test
    fun `test 1`() {
        // given
        val publicKey = 17807724L
        val loopNumber = 8L
        // when
        val encryptionKey = calculateEncryptionKey(publicKey, loopNumber)
        // then
        expectThat(encryptionKey).isEqualTo(14897079L)
    }

    @Test
    fun `test 2`() {
        // given
        val publicKey = 5764801L
        val loopNumber = 11L
        // when
        val encryptionKey = calculateEncryptionKey(publicKey, loopNumber)
        // then
        expectThat(encryptionKey).isEqualTo(14897079L)
    }
}