package day2

import java.io.File

object Loader {
    private val pattern = "(\\d+)-(\\d+) ([a-zA-Z]{1}): ([a-zA-Z]+)"
    private val regex = Regex(pattern)

    fun load(): List<Pair<PasswordPolicy, Password>> {
        val inputFileUrl = this::class.java.classLoader.getResource("day2.txt")
        return File(inputFileUrl.toURI()).useLines { it.map { buildInput(it) }.toList() }
    }

    private fun buildInput(line: String): Pair<PasswordPolicy, Password> {
        val match = regex.find(line)!!
        val (lowerBound, upperBound, letter, passwordValue) = match.destructured
        val policy = PasswordPolicy(letter.toCharArray()[0], lowerBound.toInt(), upperBound.toInt())
        val password = Password(passwordValue)
        return policy to password
    }
}

fun main() {
    val input = Loader.load()

    val oldValidator = OldPasswordValidator()
    val oldValidPasswords = input.count { oldValidator.validate(it.second, it.first) }
    println("Old-Valid passwords = $oldValidPasswords")

    val newValidator = NewPasswordValidator()
    val newValidPasswords = input.count { newValidator.validate(it.second, it.first) }
    println("New-Valid passwords = $newValidPasswords")
}