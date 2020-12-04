package day4

interface FieldValidator {
    fun validatesField(fieldName: String): Boolean
    fun isValid(document: Document): Boolean
}

object PassAllFieldValidator : FieldValidator {
    override fun validatesField(fieldName: String): Boolean = true

    override fun isValid(document: Document): Boolean = true
}

abstract class AbstractFieldValidator(private val validatedFieldName: String) : FieldValidator {
    override fun validatesField(fieldName: String): Boolean = fieldName == validatedFieldName
    protected abstract fun isValueValid(fieldValue: String): Boolean
    final override fun isValid(document: Document): Boolean = isValueValid(document.contents[validatedFieldName]!!)
}

abstract class YearRangeValidator(private val range: Pair<Int, Int>, validatedFieldName: String) :
    AbstractFieldValidator(validatedFieldName) {
    override fun isValueValid(fieldValue: String): Boolean {
        val hasCorrectLength = fieldValue.length == 4
        val hasCorrectDate = fieldValue.toIntOrNull()?.let {
            it in range.first..range.second
        } ?: false
        return hasCorrectDate && hasCorrectLength
    }
}

class BirthYearValidator : YearRangeValidator(1920 to 2002, "byr")

class IssueYearValidator : YearRangeValidator(2010 to 2020, "iyr")

