package day12

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