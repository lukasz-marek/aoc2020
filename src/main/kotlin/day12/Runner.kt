package day12

import java.io.File
import kotlin.math.absoluteValue

object Loader {

    fun load(): List<NavigationInstruction> {
        val inputFileUrl = this::class.java.classLoader.getResource("day12.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { parseInstruction(it) }.toList() }
    }
}


fun main() {
    val input = Loader.load()
    val interpreter = InstructionExecutorImpl(State(direction = Direction.EAST))
    val finalState = interpreter.execute(input)
    println("Manhattan distance is ${finalState.manhattanDistance()}")
}

private fun State.manhattanDistance(): Int {
    val grouped = path.groupBy { it.first }.mapValues { it.value.map { pair -> pair.second }.sum() }
    val verticalDistance = with(grouped) { this[Direction.NORTH]!! - this[Direction.SOUTH]!! }.absoluteValue
    val horizontalDistance = with(grouped) { this[Direction.EAST]!! - this[Direction.WEST]!! }.absoluteValue
    return verticalDistance + horizontalDistance
}