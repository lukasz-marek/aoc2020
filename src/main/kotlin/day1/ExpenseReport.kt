package day1

typealias Entry = Int

data class ExpenseReport(val expenses: List<Entry>)

interface ExpenseReportFixer {
    fun findEntriesWithSumEqualTo(report: ExpenseReport, expected: Entry): Pair<Entry, Entry>
}

class ExpenseReportFixerImpl : ExpenseReportFixer {
    override fun findEntriesWithSumEqualTo(report: ExpenseReport, expected: Entry): Pair<Entry, Entry> {
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

}