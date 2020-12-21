package day21

sealed class AbstractAllergen
object NoAllergen : AbstractAllergen()
data class Allergen(val name: String) : AbstractAllergen()
inline class Ingredient(val name: String)
data class Food(val ingredients: Set<Ingredient>, val allergens: Set<AbstractAllergen>)

private val foodPattern = Regex("^(.+) \\(contains (.+)\\)$")
fun parseFood(food: String): Food {
    val (ingredientsPart, allergensPart) = foodPattern.matchEntire(food)!!.destructured
    val ingredients =
        ingredientsPart.split(" ").asSequence().filter { it.isNotEmpty() }.map { it.trim() }.map { Ingredient(it) }
            .toSet()
    val allergens =
        allergensPart.split(",").asSequence().filter { it.isNotEmpty() }.map { it.trim() }.map { Allergen(it) }
            .toSet()
    return Food(ingredients, allergens)
}


interface Solver {
    fun solve(foods: List<Food>): Map<Ingredient, AbstractAllergen>?
}

class SimpleRecursiveSolver : Solver {

    override fun solve(foods: List<Food>): Map<Ingredient, AbstractAllergen>? {
        val allIngredients = foods.flatMap { it.ingredients }.toSet()
        val allAllergens = foods.flatMap { it.allergens }.toSet()
        return solve(foods, allIngredients, allAllergens, emptyMap())
    }

    private fun solve(
        foods: List<Food>,
        ingredients: Set<Ingredient>,
        allergens: Set<AbstractAllergen>,
        solution: Map<Ingredient, AbstractAllergen>
    ): Map<Ingredient, AbstractAllergen>? {
        if (ingredients.isEmpty()) {
            return solution
        }
        val ingredient = ingredients.first()
        val remainingIngredients = ingredients.drop(1).toSet()
        for (allergen in allergens + NoAllergen) {
            val currentSolution = solution + (ingredient to allergen)
            val noConflicts = foods.all { it.isSatisfiable(currentSolution) }
            if (noConflicts) {
                val maybeSolution = solve(foods, remainingIngredients, allergens - allergen, currentSolution)
                if (maybeSolution != null) {
                    return maybeSolution
                }
            }
        }
        return null
    }

    private fun Food.isSatisfiable(mapping: Map<Ingredient, AbstractAllergen>): Boolean {
        val expectedAllergens = this.allergens
        val detectedAllergens = this.ingredients.mapNotNull { mapping[it] }
        return detectedAllergens.containsAll(expectedAllergens) || detectedAllergens.size < ingredients.size
    }
}

class SmartRecursiveSolver : Solver {
    override fun solve(foods: List<Food>): Map<Ingredient, AbstractAllergen>? {
        val ingredients = foods.asSequence().flatMap { it.ingredients }.distinct()
            .sortedBy { ingredient -> foods.count { it.ingredients.contains(ingredient) } }.toList()
        val allergens = foods.flatMap { it.allergens }.toSet()
        val foodsByIngredient = ingredients.map { it to foods.filter { food -> food.ingredients.contains(it) } }.toMap()
        return solve(foodsByIngredient, ingredients, allergens, mutableMapOf())
    }

    private fun solve(
        foodsByIngredient: Map<Ingredient, List<Food>>,
        ingredients: List<Ingredient>,
        allergens: Set<AbstractAllergen>,
        solution: MutableMap<Ingredient, AbstractAllergen>
    ): Map<Ingredient, AbstractAllergen>? {
        if (ingredients.isEmpty()) {
            return solution
        }

        val ingredient = ingredients.first()
        val remainingIngredients = ingredients.drop(1)
        val foods = foodsByIngredient[ingredient]!!

        for (allergen in allergens + NoAllergen) {
            solution[ingredient] = allergen
            val noConflicts = foods.all { !it.isConflicting(solution) }
            if (noConflicts) {
                val maybeSolution =
                    solve(foodsByIngredient, remainingIngredients, allergens - allergen, solution)
                if (maybeSolution != null) {
                    return maybeSolution
                }
            }
        }
        return null
    }

    private fun Food.isConflicting(mapping: Map<Ingredient, AbstractAllergen>): Boolean {
        val expectedAllergens = this.allergens
        val detectedAllergens = this.ingredients.mapNotNull { mapping[it] }
        return detectedAllergens.containsAll(expectedAllergens) || expectedAllergens.asSequence()
            .filterNot { it in detectedAllergens }.any { it in mapping.values }
    }

}

