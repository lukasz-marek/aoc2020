package day1

typealias Entry = Int

data class ExpenseReport(val expenses: List<Entry>)

interface ExpenseReportFixer {
    fun findTwoEntriesWithSumEqualTo(report: ExpenseReport, expected: Entry): Pair<Entry, Entry>
    fun findThreeEntriesWithSumEqualTo(report: ExpenseReport, expected: Entry): List<Entry>

}

class ExpenseReportFixerImpl : ExpenseReportFixer {
    override fun findTwoEntriesWithSumEqualTo(report: ExpenseReport, expected: Entry): Pair<Entry, Entry> {
        for ((index, entry1) in report.expenses.withIndex()) {
            for (entry2 in report.expenses.drop(index + 1)) {
                val sum = entry1 + entry2
                if (sum == expected) {
                    return Pair(entry1, entry2)
                }
            }
        }
        throw IllegalStateException("Couldn't find entries that equal $expected")
    }

    override fun findThreeEntriesWithSumEqualTo(report: ExpenseReport, expected: Entry): List<Entry> {
        for ((index1, entry1) in report.expenses.withIndex()) {
            for ((index2, entry2) in report.expenses.withIndex().drop(index1 + 1)) {
                for (entry3 in report.expenses.drop(index2 + 1)) {
                    val sum = entry1 + entry2 + entry3
                    if (sum == expected) {
                        return listOf(entry1, entry2, entry3)
                    }
                }
            }
        }
        throw IllegalStateException("Couldn't find entries that equal $expected")
    }

}