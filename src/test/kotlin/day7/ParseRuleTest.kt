package day7

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ParseRuleTest {

    @Test
    fun `should successfully parse empty rule 1`() {
        // given
        val rule = "faded blue bags contain no other bags."
        // when
        val parsedRule = parseRule(rule)
        // then
        expectThat(parsedRule) {
            get { describedBag }.isEqualTo(Bag("faded blue"))
            get { containedBags }.isEqualTo(emptyMap())
        }
    }

    @Test
    fun `should successfully parse empty rule 2`() {
        // given
        val rule = "dotted black bags contain no other bags."
        // when
        val parsedRule = parseRule(rule)
        // then
        expectThat(parsedRule) {
            get { describedBag }.isEqualTo(Bag("dotted black"))
            get { containedBags }.isEqualTo(emptyMap())
        }
    }

    @Test
    fun `should successfully parse non-empty rule 1`() {
        // given
        val rule = "bright white bags contain 1 shiny gold bag."
        // when
        val parsedRule = parseRule(rule)
        // then
        expectThat(parsedRule) {
            get { describedBag }.isEqualTo(Bag("bright white"))
            get { containedBags }.isEqualTo(mapOf(Bag("shiny gold") to 1))
        }
    }

    @Test
    fun `should successfully parse non-empty rule 2`() {
        // given
        val rule = "light red bags contain 1 bright white bag, 2 muted yellow bags."
        // when
        val parsedRule = parseRule(rule)
        // then
        expectThat(parsedRule) {
            get { describedBag }.isEqualTo(Bag("light red"))
            get { containedBags }.isEqualTo(mapOf(Bag("bright white") to 1, Bag("muted yellow") to 2))
        }
    }

    @Test
    fun `should successfully parse non-empty rule 3`() {
        // given
        val rule = "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags."
        // when
        val parsedRule = parseRule(rule)
        // then
        expectThat(parsedRule) {
            get { describedBag }.isEqualTo(Bag("vibrant plum"))
            get { containedBags }.isEqualTo(mapOf(Bag("faded blue") to 5, Bag("dotted black") to 6))
        }
    }
}