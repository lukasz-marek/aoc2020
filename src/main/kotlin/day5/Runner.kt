package day5

import java.io.File

object Loader {

    fun load(): List<EncodedSeat> {
        val inputFileUrl = this::class.java.classLoader.getResource("day5.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { parseSeat(it) }.toList() }
    }
}

fun main() {
    val inputs = Loader.load()
    val rowDecoder = PartitionDecoderImpl(0 to 127)
    val columnDecoder = PartitionDecoderImpl(0 to 7)
    val seatDecoder = SeatDecoderImpl(rowDecoder = rowDecoder, columnDecoder = columnDecoder)
    val decodedSeats = inputs.map { seatDecoder.decode(it) }
    val highestSeatId = decodedSeats.maxByOrNull { it.seatId }?.seatId
    println("Highest seat id is $highestSeatId")

    val mySeatId = findMySeatId(decodedSeats)
    println("My seat id is $mySeatId")
}

private fun findMySeatId(seats: List<DecodedSeat>): Int {
    val takenSeatIds = seats.map { it.seatId }.toSet()
    val minimalSeatId = takenSeatIds.minByOrNull { it }!!
    val maximalSeatId = takenSeatIds.maxByOrNull { it }!!
    for (seatId in minimalSeatId..maximalSeatId) {
        if (!takenSeatIds.contains(seatId) && takenSeatIds.contains(seatId - 1) && takenSeatIds.contains(seatId + 1)) {
            return seatId
        }
    }
    throw IllegalStateException("Failed to find my seat!")
}