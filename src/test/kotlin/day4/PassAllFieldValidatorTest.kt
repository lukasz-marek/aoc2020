package day4

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isTrue

class PassAllFieldValidatorTest {
    private val tested = PassAllFieldValidator

    @ParameterizedTest
    @ValueSource(strings = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"])
    fun `all fields should be testable`(fieldName: String) {
        // given / when
        val canBeTested = tested.validatesField(fieldName)
        // then
        expectThat(canBeTested).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["ewrfg", "324wergh", "fdgdfgdf", "edywwfg", "orlgo", "ecl"])
    fun `all values should be valid`(fieldValue: String) {
        // given
        val document = Document(mapOf("field" to fieldValue))
        // / when
        val canBeTested = tested.isValid(document)
        // then
        expectThat(canBeTested).isTrue()
    }
}