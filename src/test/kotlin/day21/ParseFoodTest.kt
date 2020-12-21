package day21

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ParseFoodTest {
    @Test
    fun `should parse successfully 1`(){
    // given
        val food ="sqjhc fvjkl (contains soy)"
        // when
        val parsed = parseFood(food)
        // then
        expectThat(parsed).isEqualTo(Food(
            ingredients = setOf(Ingredient("sqjhc"),Ingredient("fvjkl")),
        allergens = setOf(ConcreteAllergen("soy"))))
    }

    @Test
    fun `should parse successfully 2`(){
        // given
        val food ="mxmxvkd kfcds sqjhc nhms (contains dairy, fish)"
        // when
        val parsed = parseFood(food)
        // then
        expectThat(parsed).isEqualTo(Food(
            ingredients = setOf(Ingredient("mxmxvkd"),Ingredient("kfcds"),Ingredient("sqjhc"),Ingredient("nhms")),
            allergens = setOf(ConcreteAllergen("dairy"),ConcreteAllergen("fish"))))
    }
}