package day17

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestIterate {

    private val input =
        """.#.
        ..#
        ###""".lines().map { it.trim() }.map { line ->
            line.map {
                when (it) {
                    '#' -> Cube(true)
                    else -> Cube(false)
                }
            }.toMutableList()
        }.toMutableList()
            .let { Grid2D(it) }

    @Test
    fun `test 1`() {
        //given
        val grid3d = Grid3D(mutableListOf(input))
        // when
        val output = iterate(grid3d, 6)
        // then
        expectThat(output.sections.flatMap { it.cubes.flatten() }.count { it.isAlive }).isEqualTo(112)
    }
}