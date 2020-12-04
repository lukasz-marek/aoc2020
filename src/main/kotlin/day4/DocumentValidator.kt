package day4

data class Document(val contents: Map<String, String>)

interface DocumentValidator {
    fun isValid(document: Document): Boolean
}

class DocumentValidatorImpl(
    private val requiredFields: Set<String>,
    private val validators: List<FieldValidator> = listOf(PassAllFieldValidator)
) : DocumentValidator {

    override fun isValid(document: Document): Boolean {
        val hasValidStructure = requiredFields.all { document.contents.containsKey(it) }
        val validateFields = { ->
            val applicableValidators = validators.filter { fieldValidator ->
                document.contents.keys.any { fieldValidator.validatesField(it) }
            }
            applicableValidators.all { it.isValid(document) }
        }
        return hasValidStructure && validateFields()
    }
}

fun parseDocument(document: String): Document {
    val contents = document.split("\n")
        .flatMap {
            it.split(" ")
                .map { it.trim() }
        }
        .map { it.split(":") }
        .map { it[0] to it[1] }.toMap()
    return Document(contents)
}

fun identifyDocuments(lines: List<String>): List<String> {
    val documents = mutableListOf<String>()
    val documentInProgress = mutableListOf<String>()

    for (line in lines) {
        if (line.isEmpty()) {
            val document = documentInProgress.joinToString(separator = "\n")
            documents.add(document)
            documentInProgress.clear()
        } else {
            documentInProgress.add(line.trim())
        }
    }
    return documents.toList()
}