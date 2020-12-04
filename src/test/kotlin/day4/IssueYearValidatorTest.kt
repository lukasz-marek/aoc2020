package day4

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class IssueYearValidatorTest {
    private val tested = IssueYearValidator()

    @Test
    fun `issue year should be identified as verifiable`() {
        // given
        val fieldName = "iyr"

        // when
        val isVerifiable = tested.validatesField(fieldName)

        // then
        expectThat(isVerifiable).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["2010", "2012", "2018", "2020"])
    fun `valid issue year should be identified`(issueYear: String) {
        // given
        val document = Document(mapOf("iyr" to issueYear))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isTrue()
    }

    @ParameterizedTest
    @ValueSource(strings = ["02020", "notANumber", "20100", "2137", "1792"])
    fun `invalid birth year should be identified`(birthYear: String) {
        // given
        val document = Document(mapOf("iyr" to birthYear))

        // when
        val isValid = tested.isValid(document)

        // then
        expectThat(isValid).isFalse()
    }
}