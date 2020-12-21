package day21

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull


class SimpleRecursiveSolverTest {
    private val tested = SimpleRecursiveSolver()

    @Test
    fun `should return valid results for simple problem`() {
//        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
//        trh fvjkl sbzzf mxmxvkd (contains dairy)
//        sqjhc fvjkl (contains soy)
//        sqjhc mxmxvkd sbzzf (contains fish)
        // given
        val foods = listOf(
            Food(
                setOf("mxmxvkd", "kfcds", "sqjhc", "nhms").map { Ingredient(it) }.toSet(),
                setOf("dairy", "fish").map { Allergen(it) }.toSet()
            ),
            Food(
                setOf("trh", "fvjkl", "sbzzf", "mxmxvkd").map { Ingredient(it) }.toSet(),
                setOf("dairy").map { Allergen(it) }.toSet()
            ),
            Food(
                setOf("sqjhc", "fvjkl").map { Ingredient(it) }.toSet(),
                setOf("soy").map { Allergen(it) }.toSet()
            ),
            Food(
                setOf("sqjhc", "mxmxvkd", "sbzzf").map { Ingredient(it) }.toSet(),
                setOf("fish").map { Allergen(it) }.toSet()
            )
        )
        // when
        val solution = tested.solve(foods)
        // then
        expectThat(solution).isNotNull().and {
//            kfcds, nhms, sbzzf, or trh
            get { get(Ingredient("kfcds")) }.isEqualTo(NoAllergen)
            get { get(Ingredient("nhms")) }.isEqualTo(NoAllergen)
            get { get(Ingredient("sbzzf")) }.isEqualTo(NoAllergen)
            get { get(Ingredient("trh")) }.isEqualTo(NoAllergen)
        }
    }
}