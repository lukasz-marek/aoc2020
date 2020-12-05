package day5

enum class StepDirection {
    Lower, Upper
}

data class EncodedSeat(val row: List<StepDirection>, val column: List<StepDirection>)

fun parseSeat(value: String): EncodedSeat {
    fun charToDirection(value: Char, lowerSymbol: Char, upperSymbol: Char): StepDirection =
        when (value) {
            lowerSymbol -> StepDirection.Lower
            upperSymbol -> StepDirection.Upper
            else -> throw IllegalArgumentException("$value is not an expected value, please use '$lowerSymbol' or '$upperSymbol'!")
        }

    val row = value.substring(0..6)
    val parsedRow = row.toList().map { charToDirection(it, 'F', 'B') }
    val column = value.substring(7 until value.length)
    val parsedColumn = column.toList().map { charToDirection(it, 'L', 'R') }

    return EncodedSeat(parsedRow, parsedColumn)
}