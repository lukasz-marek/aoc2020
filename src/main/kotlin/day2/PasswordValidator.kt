package day2

data class PasswordPolicy(val letter: Char, val lowerBound: Int, val upperBound: Int) {
    init {
        check(lowerBound <= upperBound)
    }
}

data class Password(val value: String)

interface PasswordValidator {
    fun validate(password: Password, policy: PasswordPolicy): Boolean
}

class PasswordValidatorImpl : PasswordValidator {
    override fun validate(password: Password, policy: PasswordPolicy): Boolean{
        val timesPresent = password.value.count { it == policy.letter }
        return policy.run {
            timesPresent in lowerBound..upperBound
        }
    }

}