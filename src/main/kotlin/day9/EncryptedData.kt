package day9

data class EncryptedData(val numbers: List<Int>, val preambleSize: Int)
interface InvalidNumberFinder {
    fun findInvalidNumbers(encryptedData: EncryptedData): List<Int>
}

class InvalidNumberFinderImpl : InvalidNumberFinder {
    override fun findInvalidNumbers(encryptedData: EncryptedData): List<Int> {
        val preamble = with(encryptedData) {
            numbers.take(preambleSize)
        }
        val remainingMessage = with(encryptedData) {
            numbers.drop(preambleSize)
        }
        return scanForInvalidNumbers(preamble, remainingMessage, emptyList())
    }

    private tailrec fun scanForInvalidNumbers(
        preamble: List<Int>,
        encryptedData: List<Int>,
        collected: List<Int>
    ): List<Int> {
        if (encryptedData.isEmpty()) {
            return collected
        }
        val testedNumber = encryptedData.first()

        val newCollected = if (testedNumber.isValidNumber(preamble)) collected else collected + testedNumber
        val newPreamble = preamble.drop(1) + testedNumber
        val newEncryptedData = encryptedData.drop(1)

        return scanForInvalidNumbers(newPreamble, newEncryptedData, newCollected)
    }

    private fun Int.isValidNumber(preamble: List<Int>): Boolean {
        val withIndex = preamble.withIndex().toList()
        return withIndex.asSequence()
            .map { indexed -> indexed to withIndex.filter { it.index != indexed.index } }
            .map { indexed -> indexed.first.value to indexed.second.map { it.value } }
            .any { pair ->
                val term = pair.first
                val terms = pair.second
                terms.any { term + it == this }
            }
    }
}