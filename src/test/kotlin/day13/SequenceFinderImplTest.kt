package day13

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SequenceFinderImplTest {

    private val tested = BacktrackingSequenceFinder()

    @Test
    fun `test 1`() {
        // given
        val input = listOf(
            DepartureConstraint(Bus(17), 0),
            DepartureConstraint(Bus(13), 2),
            DepartureConstraint(Bus(19), 3)
        )
        // when
        val result = tested.find(input)
        // then
        expectThat(result).isEqualTo(3417)
    }

    @Test
    fun `test 2`() {
        // given
        val input = listOf(
            DepartureConstraint(Bus(67), 0),
            DepartureConstraint(Bus(7), 1),
            DepartureConstraint(Bus(59), 2),
            DepartureConstraint(Bus(61), 3)
        )
        // when
        val result = tested.find(input)
        // then
        expectThat(result).isEqualTo(754018)
    }

    @Test
    fun `test 3`() {
        // given 67,x,7,59,61
        // given
        val input = listOf(
            DepartureConstraint(Bus(67), 0),
            DepartureConstraint(Bus(7), 2),
            DepartureConstraint(Bus(59), 3),
            DepartureConstraint(Bus(61), 4)
        )
        // when
        val result = tested.find(input)
        // then
        expectThat(result).isEqualTo(779210)
    }

    @Test
    fun `test 4`() {
        // 67,7,x,59,61
        val input = listOf(
            DepartureConstraint(Bus(67), 0),
            DepartureConstraint(Bus(7), 1),
            DepartureConstraint(Bus(59), 3),
            DepartureConstraint(Bus(61), 4)
        )
        // when
        val result = tested.find(input)
        // then
        expectThat(result).isEqualTo(1261476)
    }

    @Test
    fun `test 5`() {
        // 1789,37,47,1889
        val input = listOf(
            DepartureConstraint(Bus(1789), 0),
            DepartureConstraint(Bus(37), 1),
            DepartureConstraint(Bus(47), 2),
            DepartureConstraint(Bus(1889), 3)
        )
        // when
        val result = tested.find(input)
        // then
        expectThat(result).isEqualTo(1202161486)
    }

    @Test
    fun `test 6`() {
        // 7,13,x,x,59,x,31,19
        val input = listOf(
            DepartureConstraint(Bus(7), 0),
            DepartureConstraint(Bus(13), 1),
            DepartureConstraint(Bus(59), 4),
            DepartureConstraint(Bus(31), 6),
            DepartureConstraint(Bus(19), 7)
        )
        // when
        val result = tested.find(input)
        // then
        expectThat(result).isEqualTo(1068781)
    }
}