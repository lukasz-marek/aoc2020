package day4

import java.io.File

object Loader {

    fun load(): List<Document> {
        val inputFileUrl = this::class.java.classLoader.getResource("day4.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines ->
                identifyDocuments(lines.toList())
                    .map { parseDocument(it) }
            }
    }
}

fun main() {
    val inputDocuments = Loader.load()
    val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    val validator = DocumentValidatorImpl(requiredFields)
    val validDocumentsCount = inputDocuments.count { validator.isValid(it) }
    println("Valid documents count: $validDocumentsCount")

    val fieldValidators = listOf(
        BirthYearValidator(), ExpirationYearValidator(),
        IssueYearValidator(), HairColorValidator(),
        HeightValidator(), PassportIdValidator(),
        EyeColorValidator()
    )
    val strictValidator = DocumentValidatorImpl(requiredFields, fieldValidators)
    val strictlyValidDocumentsCount = inputDocuments.count { strictValidator.isValid(it) }
    println("Strictly Valid documents count: $strictlyValidDocumentsCount")
}