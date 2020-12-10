package day10

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ArrangementsCounterImplTest {
    private val tested = ArrangementsCounterImpl()

    @Test
    fun `should successfully count 1`() {
        // given
        val adapters =
            """16
        10
        15
        5
        1
        11
        7
        19
        6
        12
        4""".split("\n")
                .map { it.trim().toInt() }
                .map { Adapter(it) }
        // when
        val result = tested.count(adapters)
        // then
        expectThat(result).isEqualTo(8)
    }

    @Test
    fun `should successfully count 2`() {
        // given
        val adapters =
            """28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3""".split("\n")
                .map { it.trim().toInt() }
                .map { Adapter(it) }
        // when
        val result = tested.count(adapters)
        // then
        expectThat(result).isEqualTo(19208)
    }
}