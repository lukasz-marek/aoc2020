package day21

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull


class SmartRecursiveSolverTest {
    private val tested = SmartRecursiveSolver()

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
                setOf("dairy", "fish").map { ConcreteAllergen(it) }.toSet()
            ),
            Food(
                setOf("trh", "fvjkl", "sbzzf", "mxmxvkd").map { Ingredient(it) }.toSet(),
                setOf("dairy").map { ConcreteAllergen(it) }.toSet()
            ),
            Food(
                setOf("sqjhc", "fvjkl").map { Ingredient(it) }.toSet(),
                setOf("soy").map { ConcreteAllergen(it) }.toSet()
            ),
            Food(
                setOf("sqjhc", "mxmxvkd", "sbzzf").map { Ingredient(it) }.toSet(),
                setOf("fish").map { ConcreteAllergen(it) }.toSet()
            )
        )
        // when
        val solution = tested.solve(foods)
        // then
        expectThat(solution).isNotNull().and {
//            kfcds, nhms, sbzzf, or trh
            get { get(Ingredient("kfcds")) }.isEqualTo(EmptyAllergen)
            get { get(Ingredient("nhms")) }.isEqualTo(EmptyAllergen)
            get { get(Ingredient("sbzzf")) }.isEqualTo(EmptyAllergen)
            get { get(Ingredient("trh")) }.isEqualTo(EmptyAllergen)
        }
    }
}