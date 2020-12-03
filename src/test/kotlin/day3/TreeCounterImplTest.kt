package day3

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TreeCounterImplTest {
    private val tested = TreeCounterImpl()

    @Test
    fun `should successfully count trees`() {
        // given
        val input = """
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
        """.split("\n")
            .filter { it.isNotBlank() }
            .map { it.trim().toList() }
            .map {
                it.map { char ->
                    when (char) {
                        '.' -> Field.EMPTY
                        '#' -> Field.TREE
                        else -> throw IllegalStateException("Encountered '$char'")
                    }
                }
            }
            .toList()

        // when
        val result = tested.countTrees(Grid(input))

        // then
        expectThat(result).isEqualTo(7)
    }
}