package day9

data class EncryptedData(val numbers: List<Long>, val preambleSize: Int)
interface InvalidNumberFinder {
    fun findInvalidNumbers(encryptedData: EncryptedData): List<Long>
}

class InvalidNumberFinderImpl : InvalidNumberFinder {
    override fun findInvalidNumbers(encryptedData: EncryptedData): List<Long> {
        val preamble = with(encryptedData) {
            numbers.take(preambleSize)
        }
        val remainingMessage = with(encryptedData) {
            numbers.drop(preambleSize)
        }
        return scanForInvalidNumbers(preamble, remainingMessage, emptyList())
    }

    private tailrec fun scanForInvalidNumbers(
        preamble: List<Long>,
        encryptedData: List<Long>,
        collected: List<Long>
    ): List<Long> {
        if (encryptedData.isEmpty()) {
            return collected
        }
        val testedNumber = encryptedData.first()

        val newCollected = if (testedNumber.isValidNumber(preamble)) collected else collected + testedNumber
        val newPreamble = preamble.drop(1) + testedNumber
        val newEncryptedData = encryptedData.drop(1)

        return scanForInvalidNumbers(newPreamble, newEncryptedData, newCollected)
    }

    private fun Long.isValidNumber(preamble: List<Long>): Boolean {
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

interface ContiguousSetFinder {
    fun findContiguousSet(sumEqualTo: Long, sequence: List<Long>): List<Long>
}

class ContiguousSetFinderImpl : ContiguousSetFinder {
    override fun findContiguousSet(sumEqualTo: Long, sequence: List<Long>): List<Long> {
        for (i in sequence.indices) {
            for (j in sequence.indices) {
                if (i < j) {
                    val subset = sequence.subList(i, j + 1)
                    if (subset.sum() == sumEqualTo) {
                        return subset
                    }
                }
            }
        }
        return emptyList()
    }

}