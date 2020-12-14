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

data class State(val mask: Bitmask, val memory: Map<Long, Long>)

interface InitializationInstruction {
    fun execute(state: State): State
}

class SetMaskInstruction(mask: String): InitializationInstruction {
    private val mask by lazy { Bitmask(mask) }
    override fun execute(state: State): State = state.copy(mask = mask)
}

class SetValueInstruction(private val address: Long, private val value: Long) : InitializationInstruction {
    override fun execute(state: State): State {
        val maskedValue = state.mask(value)
        val newMemory = state.memory + (address to maskedValue)
        return state.copy(memory = newMemory)
    }
}

private val setMaskInstructionPattern = Regex("^mask = ([X01]+)$")
private val setValueInstructionPattern = Regex("^mem\\[(\\d+)] = (\\d+)$")
fun parseInstruction(instruction: String): InitializationInstruction =
    when {
        instruction.matches(setMaskInstructionPattern) ->{
            val (maskValue, _) = setMaskInstructionPattern.matchEntire(instruction)!!.destructured
            SetMaskInstruction(maskValue)
        }
        instruction.matches(setValueInstructionPattern) -> {
            val (address, value) = setValueInstructionPattern.matchEntire(instruction)!!.destructured
            SetValueInstruction(address = address.toLong(), value = value.toLong())
        }
        else -> throw IllegalArgumentException("`$instruction` is not supported")
    }
