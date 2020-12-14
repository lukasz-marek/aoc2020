package day14

import java.math.BigInteger

class BitmaskV2(private val maskValue: String) {
    private val initialMask = maskValue.map {
        when (it) {
            'X' -> 0
            else -> it
        }
    }.joinToString(prefix = "", separator = "", postfix = "")
        .let { BigInteger(it, 2) }
        .longValueExact()
    
    private val overridingMasks by lazy {
        val replacements = maskValue.count { it == 'X' }
        var masks = listOf(maskValue.replace('1', 'Y').replace('0', 'Y'))
        for (i in 1..replacements) {
            masks = masks.flatMap { listOf(it.replaceFirst('X', '1'), it.replaceFirst('X', '0')) }
        }
        masks.map { it.replace('Y', 'X') }
    }

    operator fun invoke(address: Long): Iterable<Long> {
        val initiallyMasked = address or initialMask
        val masks = overridingMasks
        return masks.map { Bitmask(it)(initiallyMasked) }
    }

}

data class StateV2(val mask: BitmaskV2, val memory: Map<Long, Long>)

interface InitializationInstructionV2 {
    fun execute(state: StateV2): StateV2
}

class SetMaskInstructionV2(mask: String) : InitializationInstructionV2 {
    private val mask by lazy { BitmaskV2(mask) }
    override fun execute(state: StateV2): StateV2 = state.copy(mask = mask)
}

class SetValueInstructionV2(private val address: Long, private val value: Long) : InitializationInstructionV2 {

    override fun execute(state: StateV2): StateV2 {
        val memory = state.memory.toMutableMap()
        for(address in state.mask.invoke(address)){
            memory[address] = value
        }
        return state.copy(memory = memory.toMap())
    }

}

private val setMaskInstructionPattern = Regex("^mask = ([X01]+)$")
private val setValueInstructionPattern = Regex("^mem\\[(\\d+)] = (\\d+)$")
fun parseInstructionV2(instruction: String): InitializationInstructionV2 =
    when {
        instruction.matches(setMaskInstructionPattern) -> {
            val (maskValue, _) = setMaskInstructionPattern.matchEntire(instruction)!!.destructured
            SetMaskInstructionV2(maskValue)
        }
        instruction.matches(setValueInstructionPattern) -> {
            val (address, value) = setValueInstructionPattern.matchEntire(instruction)!!.destructured
            SetValueInstructionV2(address = address.toLong(), value = value.toLong())
        }
        else -> throw IllegalArgumentException("`$instruction` is not supported")
    }
