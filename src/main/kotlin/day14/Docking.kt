package day14

import java.math.BigInteger

class Bitmask(private val maskValue: String) {
    private val onesAddingMask = maskValue.map {
        when (it) {
            'X' -> 0
            else -> it
        }
    }.joinToString(prefix = "", separator = "", postfix = "")
        .let { BigInteger(it, 2) }
        .longValueExact()

    private val zerosAddingMask = maskValue.map {
        when (it) {
            'X' -> 1
            else -> it
        }
    }.joinToString(prefix = "", separator = "", postfix = "")
        .let { BigInteger(it, 2) }
        .longValueExact()

    operator fun invoke(value: Long): Long = (value or onesAddingMask) and zerosAddingMask

    override fun toString(): String {
        return "Bitmask(maskValue=$maskValue)"
    }

}