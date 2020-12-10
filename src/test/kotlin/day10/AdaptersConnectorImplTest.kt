package day10

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class AdaptersConnectorImplTest {

    val tested = AdaptersConnectorImpl()

    @Test
    fun `should return correct result 1`() {
        // given
        val adaptersList =
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
        val result = tested.connect(adaptersList)
        // then
        expectThat(result).isEqualTo(mapOf(1 to 7, 3 to 5))
    }

    @Test
    fun `should return correct result 2`() {
        // given
        val adaptersList =
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
        val result = tested.connect(adaptersList)
        // then
        expectThat(result).isEqualTo(mapOf(1 to 22, 3 to 10))
    }
}