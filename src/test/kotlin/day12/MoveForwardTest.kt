package day12

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.last

class MoveForwardTest {

    @Test
    fun `should keep direction when NORTH`() {
        // given
        val tested = MoveForward(5)
        val state = State(Direction.NORTH)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { direction }.isEqualTo(Direction.NORTH)
            get { path }.hasSize(1).last().isEqualTo(Pair(Direction.NORTH, 5))
        }
    }

    @Test
    fun `should keep direction when SOUTH`() {
        // given
        val tested = MoveForward(5)
        val state = State(Direction.SOUTH)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { direction }.isEqualTo(Direction.SOUTH)
            get { path }.hasSize(1).last().isEqualTo(Pair(Direction.SOUTH, 5))
        }
    }

    @Test
    fun `should keep direction when WEST`() {
        // given
        val tested = MoveForward(5)
        val state = State(Direction.WEST)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { direction }.isEqualTo(Direction.WEST)
            get { path }.hasSize(1).last().isEqualTo(Pair(Direction.WEST, 5))
        }
    }

    @Test
    fun `should keep direction when EAST`() {
        // given
        val tested = MoveForward(5)
        val state = State(Direction.EAST)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { direction }.isEqualTo(Direction.EAST)
            get { path }.hasSize(1).last().isEqualTo(Pair(Direction.EAST, 5))
        }
    }
}