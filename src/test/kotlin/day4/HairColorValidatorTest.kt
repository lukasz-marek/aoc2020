package day4

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class HairColorValidatorTest {
    private val tested = HairColorValidator()

    @Test
    fun `hair color should be identified as verifiable`() {
        // given
        val fieldName = "hcl"

        // when
        val isVerifiable = tested.validatesField(fieldName)

        // then
        expectThat(isVerifiable).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["#111111", "#abcdef", "#123def", "#abc456"])
    fun `valid hair colors should be identified`(hairColor: String) {
        // given
        val document = Document(mapOf("hcl" to hairColor))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["#", "123456", "#123abg"])
    fun `invalid hair colors should be identified`(hairColor: String) {
        // given
        val document = Document(mapOf("hcl" to hairColor))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isFalse()
    }
}