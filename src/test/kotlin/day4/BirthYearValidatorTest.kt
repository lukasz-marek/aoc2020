package day4

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class BirthYearValidatorTest {
    private val tested = BirthYearValidator()

    @Test
    fun `birth year should be identified as verifiable`() {
        // given
        val fieldName = "byr"

        // when
        val isVerifiable = tested.validatesField(fieldName)

        // then
        expectThat(isVerifiable).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["1920", "1950", "1990", "2002"])
    fun `valid birth year should be identified`(birthYear: String) {
        // given
        val document = Document(mapOf("byr" to birthYear))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["01920", "notANumber", "19900", "2137", "1792"])
    fun `invalid birth year should be identified`(birthYear: String) {
        // given
        val document = Document(mapOf("byr" to birthYear))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isFalse()
    }
}