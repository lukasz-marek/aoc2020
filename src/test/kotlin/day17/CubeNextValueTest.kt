package day17

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class CubeNextValueTest {

    @Test
    fun `next should be alive if 2 neighbours and is alive now`() {
        // given
        val tested = Cube(true)
        val aliveNeighbours = 2
        // when
        val next = tested.nextValue(aliveNeighbours)
        // then
        expectThat(next.isAlive).isTrue()
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25])
    fun `next should not be alive if not 2 or 3 neighbours and is alive now`(aliveNeighbours: Int) {
        // given
        val tested = Cube(true)
        // when
        val next = tested.nextValue(aliveNeighbours)
        // then
        expectThat(next.isAlive).isFalse()
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25])
    fun `next should not be alive if not 2 neighbours and is not alive now`(aliveNeighbours: Int) {
        // given
        val tested = Cube(false)
        // when
        val next = tested.nextValue(aliveNeighbours)
        // then
        expectThat(next.isAlive).isFalse()
    }

    @Test
    fun `next should be alive if 3 neighbours and is alive now`() {
        // given
        val tested = Cube(true)
        val aliveNeighbours = 3
        // when
        val next = tested.nextValue(aliveNeighbours)
        // then
        expectThat(next.isAlive).isTrue()
    }

    @Test
    fun `next should be alive if 3 neighbours and is not alive now`() {
        // given
        val tested = Cube(false)
        val aliveNeighbours = 3
        // when
        val next = tested.nextValue(aliveNeighbours)
        // then
        expectThat(next.isAlive).isTrue()
    }
}