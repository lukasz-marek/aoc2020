package day2

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class OldPasswordValidatorTest {

    private val tested = OldPasswordValidator()

    @Test
    fun `test valid password 1`(){
        // given
        val policy = PasswordPolicy('a', 1,3)
        val password = Password("abcde")

        // when
        val isValid = tested.validate(password, policy)

        // then
        expectThat(isValid).isEqualTo(true)
    }

    @Test
    fun `test valid password 2`(){
        // given
        val policy = PasswordPolicy('c', 2,9)
        val password = Password("ccccccccc")

        // when
        val isValid = tested.validate(password, policy)

        // then
        expectThat(isValid).isEqualTo(true)
    }

    @Test
    fun `test invalid password`(){
        // given
        val policy = PasswordPolicy('b', 1,3)
        val password = Password("cdefg")

        // when
        val isValid = tested.validate(password, policy)

        // then
        expectThat(isValid).isEqualTo(false)
    }
}