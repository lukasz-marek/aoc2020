package day4

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class ExpirationYearValidatorTest {
    private val tested = ExpirationYearValidator()

    @Test
    fun `expiration year should be identified as verifiable`() {
        // given
        val fieldName = "eyr"

        // when
        val isVerifiable = tested.validatesField(fieldName)

        // then
        expectThat(isVerifiable).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["2020", "2021", "2027", "2030"])
    fun `valid expiration year should be identified`(expirationYear: String) {
        // given
        val document = Document(mapOf("eyr" to expirationYear))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["02030", "notANumber", "20200", "2137", "1792"])
    fun `invalid expiration year should be identified`(expirationYear: String) {
        // given
        val document = Document(mapOf("eyr" to expirationYear))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isFalse()
    }
}