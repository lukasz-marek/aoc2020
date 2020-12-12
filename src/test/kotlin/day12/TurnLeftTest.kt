package day12

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class TurnLeftTest {

    @Test
    fun `when by 90 degrees and NORTH, should turn WEST`() {
        // given
        val state = State(Direction.NORTH)
        val tested = TurnLeft(90)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.WEST)
        }
    }

    @Test
    fun `when by 180 degrees and NORTH, should turn SOUTH`() {
        // given
        val state = State(Direction.NORTH)
        val tested = TurnLeft(180)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.SOUTH)
        }
    }

    @Test
    fun `when by 270 degrees and NORTH, should turn SOUTH`() {
        // given
        val state = State(Direction.NORTH)
        val tested = TurnLeft(270)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.EAST)
        }
    }

    @Test
    fun `when by 360 degrees and NORTH, should turn NORTH`() {
        // given
        val state = State(Direction.NORTH)
        val tested = TurnLeft(360)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.NORTH)
        }
    }

    @Test
    fun `when by 90 degrees and WEST, should turn SOUTH`() {
        // given
        val state = State(Direction.WEST)
        val tested = TurnLeft(90)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.SOUTH)
        }
    }

    @Test
    fun `when by 180 degrees and WEST, should turn EAST`() {
        // given
        val state = State(Direction.WEST)
        val tested = TurnLeft(180)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.EAST)
        }
    }

    @Test
    fun `when by 270 degrees and WEST, should turn NORTH`() {
        // given
        val state = State(Direction.WEST)
        val tested = TurnLeft(270)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.NORTH)
        }
    }

    @Test
    fun `when by 360 degrees and WEST, should turn WEST`() {
        // given
        val state = State(Direction.WEST)
        val tested = TurnLeft(360)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.WEST)
        }
    }

    @Test
    fun `when by 90 degrees and SOUTH, should turn EAST`() {
        // given
        val state = State(Direction.SOUTH)
        val tested = TurnLeft(90)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.EAST)
        }
    }

    @Test
    fun `when by 180 degrees and SOUTH, should turn NORTH`() {
        // given
        val state = State(Direction.SOUTH)
        val tested = TurnLeft(180)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.NORTH)
        }
    }

    @Test
    fun `when by 270 degrees and SOUTH, should turn WEST`() {
        // given
        val state = State(Direction.SOUTH)
        val tested = TurnLeft(270)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.WEST)
        }
    }

    @Test
    fun `when by 360 degrees and SOUTH, should turn SOUTH`() {
        // given
        val state = State(Direction.SOUTH)
        val tested = TurnLeft(360)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.SOUTH)
        }
    }


    @Test
    fun `when by 90 degrees and EAST, should turn NORTH`() {
        // given
        val state = State(Direction.EAST)
        val tested = TurnLeft(90)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.NORTH)
        }
    }

    @Test
    fun `when by 180 degrees and EAST, should turn WEST`() {
        // given
        val state = State(Direction.EAST)
        val tested = TurnLeft(180)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.WEST)
        }
    }

    @Test
    fun `when by 270 degrees and EAST, should turn SOUTH`() {
        // given
        val state = State(Direction.EAST)
        val tested = TurnLeft(270)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.SOUTH)
        }
    }

    @Test
    fun `when by 360 degrees and EAST, should turn EAST`() {
        // given
        val state = State(Direction.EAST)
        val tested = TurnLeft(360)
        // when
        val result = tested.execute(state)
        // then
        expectThat(result) {
            get { path }.isEmpty()
            get { direction }.isEqualTo(Direction.EAST)
        }
    }
}