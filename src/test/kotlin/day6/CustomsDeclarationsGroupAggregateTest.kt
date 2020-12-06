package day6

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CustomsDeclarationsGroupAggregateTest {
    @Test
    fun `should successfully aggregate forms using Union`() {
        // given
        val declarations = listOf(
            DeclarationForm(setOf('a', 'b', 'c')),
            DeclarationForm(setOf('a', 'c')),
            DeclarationForm(setOf('d')),
            DeclarationForm(setOf('a'))
        )
        val group = CustomsDeclarationsGroup(declarations)
        // when
        val aggregated = group.aggregate(AggregationMode.Union)
        // then
        expectThat(aggregated.declarations).isEqualTo(setOf('a', 'b', 'c', 'd'))
    }

    @Test
    fun `should successfully aggregate forms using Intersection 1`() {
        // given
        val declarations = listOf(
            DeclarationForm(setOf('a', 'b', 'c')),
            DeclarationForm(setOf('a', 'c')),
            DeclarationForm(setOf('d')),
            DeclarationForm(setOf('a'))
        )
        val group = CustomsDeclarationsGroup(declarations)
        // when
        val aggregated = group.aggregate(AggregationMode.Intersection)
        // then
        expectThat(aggregated.declarations).isEqualTo(emptySet())
    }

    @Test
    fun `should successfully aggregate forms using Intersection 2`() {
        // given
        val declarations = listOf(
            DeclarationForm(setOf('a', 'b', 'c')),
            DeclarationForm(setOf('a', 'c')),
            DeclarationForm(setOf('a'))
        )
        val group = CustomsDeclarationsGroup(declarations)
        // when
        val aggregated = group.aggregate(AggregationMode.Intersection)
        // then
        expectThat(aggregated.declarations).isEqualTo(setOf('a'))
    }
}