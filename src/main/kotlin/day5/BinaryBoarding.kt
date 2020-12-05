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

data class DecodedSeat(val row: Int, val column: Int, val seatId: Int)

interface PartitionDecoder {
    fun decode(encoded: List<StepDirection>): Int
}

class PartitionDecoderImpl(private val range: Pair<Int, Int>) : PartitionDecoder {
    init {
        check(range.first <= range.second) { "${range.first} is not less or equal than ${range.second}" }
    }

    override fun decode(encoded: List<StepDirection>): Int = decode(encoded, range.first, range.second)

    private tailrec fun decode(encoded: List<StepDirection>, lower: Int, upper: Int): Int =
        when {
            encoded.isEmpty() && upper == lower -> upper
            encoded.isEmpty() -> throw IllegalStateException("Ambiguous input: cannot decide between $lower and $upper!")
            encoded[0] == StepDirection.Upper -> {
                val newLower = ((lower + upper) / 2) + 1
                decode(encoded.subList(1, encoded.size), newLower, upper)
            }
            encoded[0] == StepDirection.Lower -> {
                val newUpper = (lower + upper) / 2
                decode(encoded.subList(1, encoded.size), lower, newUpper)
            }
            else -> throw IllegalStateException("Decoder in illegal state!")
        }

}