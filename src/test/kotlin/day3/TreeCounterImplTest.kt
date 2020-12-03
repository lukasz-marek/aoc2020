package day3

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TreeCounterImplTest {

    private val input = """
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

    @Test
    fun `should successfully count trees 1`() {
        // given
        val grid = Grid(input)
        val tested = TreeCounterImpl(3, 1)

        // when
        val result = tested.countTrees(grid)

        // then
        expectThat(result).isEqualTo(7)
    }

    @Test
    fun `should successfully count trees 2`() {
        // given
        val grid = Grid(input)
        val tested = TreeCounterImpl(1, 1)

        // when
        val result = tested.countTrees(grid)

        // then
        expectThat(result).isEqualTo(2)
    }

    @Test
    fun `should successfully count trees 3`() {
        // given
        val grid = Grid(input)
        val tested = TreeCounterImpl(5, 1)

        // when
        val result = tested.countTrees(grid)

        // then
        expectThat(result).isEqualTo(3)
    }

    @Test
    fun `should successfully count trees 4`() {
        // given
        val grid = Grid(input)
        val tested = TreeCounterImpl(7, 1)

        // when
        val result = tested.countTrees(grid)

        // then
        expectThat(result).isEqualTo(4)
    }

    @Test
    fun `should successfully count trees 5`() {
        // given
        val grid = Grid(input)
        val tested = TreeCounterImpl(1, 2)

        // when
        val result = tested.countTrees(grid)

        // then
        expectThat(result).isEqualTo(2)
    }
}