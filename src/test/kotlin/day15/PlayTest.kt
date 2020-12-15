package day15

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PlayTest {

    @Test
    fun `test 1`() {
        // given
        val initialNumbers = listOf(0, 3, 6)
        val rounds = 10
        // when
        val result = play(initialNumbers, rounds)
        // then
        expectThat(result).isEqualTo(0)
    }

    @Test
    fun `test 2`() {
        // given
        val initialNumbers = listOf(1, 3, 2)
        val rounds = 2020
        // when
        val result = play(initialNumbers, rounds)
        // then
        expectThat(result).isEqualTo(1)
    }

    @Test
    fun `test 3`() {
        // given
        val initialNumbers = listOf(2, 1, 3)
        val rounds = 2020
        // when
        val result = play(initialNumbers, rounds)
        // then
        expectThat(result).isEqualTo(10)
    }

    @Test
    fun `test 4`() {
        // given
        val initialNumbers = listOf(1, 2, 3)
        val rounds = 2020
        // when
        val result = play(initialNumbers, rounds)
        // then
        expectThat(result).isEqualTo(27)
    }

    @Test
    fun `test 5`() {
        // given
        val initialNumbers = listOf(2, 3, 1)
        val rounds = 2020
        // when
        val result = play(initialNumbers, rounds)
        // then
        expectThat(result).isEqualTo(78)
    }

    @Test
    fun `test 6`() {
        // given
        val initialNumbers = listOf(3, 2, 1)
        val rounds = 2020
        // when
        val result = play(initialNumbers, rounds)
        // then
        expectThat(result).isEqualTo(438)
    }

    @Test
    fun `test 7`() {
        // given
        val initialNumbers = listOf(3, 1, 2)
        val rounds = 2020
        // when
        val result = play(initialNumbers, rounds)
        // then
        expectThat(result).isEqualTo(1836)
    }
}