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

class OldPasswordValidator : PasswordValidator {

    override fun validate(password: Password, policy: PasswordPolicy): Boolean {
        val timesPresent = password.value.count { it == policy.letter }
        return policy.run {
            timesPresent in lowerBound..upperBound
        }
    }

}

class NewPasswordValidator : PasswordValidator {
    override fun validate(password: Password, policy: PasswordPolicy): Boolean =
        when {
            password.value.length < policy.upperBound -> false
            else -> with(policy) {
                val atFirstPosition = password.value[lowerBound - 1]
                val atSecondPosition = password.value[upperBound - 1]
                (atFirstPosition == letter) xor (atSecondPosition == letter)
            }
        }

}