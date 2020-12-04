package day4

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class PassportIdValidatorTest {
    private val tested = PassportIdValidator()

    @Test
    fun `passport id should be identified as verifiable`() {
        // given
        val fieldName = "pid"

        // when
        val isVerifiable = tested.validatesField(fieldName)

        // then
        expectThat(isVerifiable).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["000000000", "123456789", "000111555"])
    fun `valid passport id should be identified`(passportId: String) {
        // given
        val document = Document(mapOf("pid" to passportId))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["12345678", "1234567890", "thisIsNotAPassportId"])
    fun `invalid passport ids should be identified`(passportId: String) {
        // given
        val document = Document(mapOf("pid" to passportId))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isFalse()
    }
}