package day12

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.last

class MoveInDirectionTest {
    @Test
    fun `should move NORTH by expected amount`() {
        // given
        val state = State(Direction.EAST)
        val tested = MoveInDirection(Direction.NORTH, 12)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { direction }.isEqualTo(Direction.EAST)
            get { path }.hasSize(1).last().isEqualTo(Pair(Direction.NORTH, 12))
        }
    }

    @Test
    fun `should move SOUTH by expected amount`() {
        // given
        val state = State(Direction.EAST)
        val tested = MoveInDirection(Direction.NORTH, 12)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { direction }.isEqualTo(Direction.EAST)
            get { path }.hasSize(1).last().isEqualTo(Pair(Direction.NORTH, 12))
        }
    }

    @Test
    fun `should move WEST by expected amount`() {
        // given
        val state = State(Direction.SOUTH)
        val tested = MoveInDirection(Direction.WEST, 10)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { direction }.isEqualTo(Direction.SOUTH)
            get { path }.hasSize(1).last().isEqualTo(Pair(Direction.WEST, 10))
        }
    }

    @Test
    fun `should move EAST by expected amount`() {
        // given
        val state = State(Direction.NORTH)
        val tested = MoveInDirection(Direction.EAST, 17)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { direction }.isEqualTo(Direction.NORTH)
            get { path }.hasSize(1).last().isEqualTo(Pair(Direction.EAST, 17))
        }
    }
}