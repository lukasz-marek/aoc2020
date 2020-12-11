package day11

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SimulatorImplTest {
    private val tested = SimulatorImpl()
    private val input =
        """L.LL.LL.LL
           LLLLLLL.LL
           L.L.L..L..
           LLLL.LL.LL
           L.LL.LL.LL
           L.LLLLL.LL
           ..L.L.....
           LLLLLLLLLL
           L.LLLLLL.L
           L.LLLLL.LL"""
    private val grid = parseInput(input.split("\n"))

    private val expectedOutput = """#.#L.L#.##
        #LLL#LL.L#
        L.#.L..#..
        #L##.##.L#
        #.#L.LL.LL
        #.#L#L#.##
        ..L.L.....
        #L#L##L#L#
        #.LLLLLL.L
        #.#L#L#.##
    """.trimIndent()
    private val expectedGrid = parseInput(expectedOutput.split("\n"))

    @Test
    fun `should correctly run simulations`() {
        // given / when
        val result = tested.runSimulations(grid)
        // then
        expectThat(result).isEqualTo(expectedGrid)
    }

}