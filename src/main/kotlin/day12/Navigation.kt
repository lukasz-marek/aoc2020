package day12


private val instructionPattern = Regex("(\\w)(\\d{1,3})")
fun parseInstruction(instruction: String): NavigationInstruction =
    instructionPattern.matchEntire(instruction.trim())?.let {
        val (instructionType, amount) = it.destructured
        when (instructionType) {
            "F" -> MoveForward(amount.toInt())
            "R" -> TurnRight(amount.toInt())
            "L" -> TurnLeft(amount.toInt())
            "N" -> MoveInDirection(Direction.NORTH, amount.toInt())
            "S" -> MoveInDirection(Direction.SOUTH, amount.toInt())
            "W" -> MoveInDirection(Direction.WEST, amount.toInt())
            "E" -> MoveInDirection(Direction.EAST, amount.toInt())
            else -> throw IllegalArgumentException("$instructionType is not supported")
        }
    }!!

enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

data class State(val direction: Direction, val path: List<Pair<Direction, Int>> = emptyList())

interface NavigationInstruction {
    fun execute(state: State): State
}

class MoveForward(private val amount: Int) : NavigationInstruction {
    override fun execute(state: State): State = with(state) {
        copy(path = path + Pair(direction, amount))
    }
}

class TurnLeft(private val amount: Int) : NavigationInstruction {
    init {
        assert(amount % 90 == 0)
    }

    override fun execute(state: State): State = turnLeftBy90(state, amount)

    private tailrec fun turnLeftBy90(state: State, degrees: Int): State =
        if (degrees == 0) {
            state
        } else {
            val nextDirection = when (state.direction) {
                Direction.NORTH -> Direction.WEST
                Direction.SOUTH -> Direction.EAST
                Direction.EAST -> Direction.NORTH
                Direction.WEST -> Direction.SOUTH
            }
            turnLeftBy90(state.copy(direction = nextDirection), degrees - 90)
        }

}

class TurnRight(private val amount: Int) : NavigationInstruction {
    init {
        assert(amount % 90 == 0)
    }

    override fun execute(state: State): State = turnRightBy90(state, amount)

    private tailrec fun turnRightBy90(state: State, degrees: Int): State =
        if (degrees == 0) {
            state
        } else {
            val nextDirection = when (state.direction) {
                Direction.NORTH -> Direction.EAST
                Direction.SOUTH -> Direction.WEST
                Direction.EAST -> Direction.SOUTH
                Direction.WEST -> Direction.NORTH
            }
            turnRightBy90(state.copy(direction = nextDirection), degrees - 90)
        }

}

class MoveInDirection(private val direction: Direction, private val amount: Int) : NavigationInstruction {
    override fun execute(state: State): State = with(state) {
        state.copy(path = path + Pair(this@MoveInDirection.direction, amount))
    }
}

interface InstructionExecutor {
    fun execute(instructions: List<NavigationInstruction>): State
}

class InstructionExecutorImpl(private val initialState: State) : InstructionExecutor {
    override fun execute(instructions: List<NavigationInstruction>): State =
        instructions.fold(initialState) { state, instruction -> instruction.execute(state) }
}