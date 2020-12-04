package day4

interface FieldValidator {
    fun validatesField(fieldName: String): Boolean
    fun isValid(document: Document): Boolean
}

object PassAllFieldValidator : FieldValidator {
    override fun validatesField(fieldName: String): Boolean = true

    override fun isValid(document: Document): Boolean = true
}