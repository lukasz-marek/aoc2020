package day4

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class EyeColorValidatorTest {
    private val tested = EyeColorValidator()

    @Test
    fun `eye color should be identified as verifiable`() {
        // given
        val fieldName = "ecl"

        // when
        val isVerifiable = tested.validatesField(fieldName)

        // then
        expectThat(isVerifiable).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"])
    fun `should accept colors from the list`(color: String) {
        // given
        val document = Document(mapOf("ecl" to color))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["vam", "lub", "rbn", "gyr"])
    fun `should reject colors outside of the list`(color: String) {
        // given
        val document = Document(mapOf("ecl" to color))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isFalse()
    }
}