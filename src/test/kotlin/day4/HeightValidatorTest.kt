package day4

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class HeightValidatorTest {
    private val tested = HeightValidator()

    @Test
    fun `height should be identified as verifiable`() {
        // given
        val fieldName = "hgt"

        // when
        val isVerifiable = tested.validatesField(fieldName)

        // then
        expectThat(isVerifiable).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["150cm", "169cm", "193cm", "59in", "67in", "76in"])
    fun `valid heights should be identified`(height: String){
        // given
        val document = Document(mapOf("hgt" to height))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["130cm", "thisIsNotEvenAHeight", "200cm", "9in", "80in"])
    fun `invalid heights should be identified`(height: String){
        // given
        val document = Document(mapOf("hgt" to height))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isFalse()
    }
}