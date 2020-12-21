package day21

import kotlin.math.exp

sealed class Allergen
object EmptyAllergen : Allergen()
data class ConcreteAllergen(val name: String) : Allergen()
inline class Ingredient(val name: String)
data class Food(val ingredients: Set<Ingredient>, val allergens: Set<Allergen>)

interface Solver {
    fun solve(foods: List<Food>): Map<Ingredient, Allergen>?
}

class SimpleRecursiveSolver : Solver {

    override fun solve(foods: List<Food>): Map<Ingredient, Allergen>? {
        val allIngredients = foods.flatMap { it.ingredients }.toSet()
        val allAllergens = foods.flatMap { it.allergens }.toSet()
        return solve(foods,allIngredients, allAllergens, emptyMap())
    }

    private fun solve(foods: List<Food>,
                      ingredients: Set<Ingredient>,
                      allergens: Set<Allergen>,
                      solution: Map<Ingredient,Allergen>): Map<Ingredient, Allergen>? {
        if(ingredients.isEmpty()){
            return solution
        }
        val ingredient = ingredients.first()
        val remainingIngredients = ingredients.drop(1).toSet()
        for(allergen in allergens + EmptyAllergen){
            val currentSolution = solution + (ingredient to allergen)
            val noConflicts = foods.all { it.isSatisfiable(currentSolution) }
            if(noConflicts){
                val maybeSolution = solve(foods, remainingIngredients, allergens - allergen, currentSolution)
                if(maybeSolution != null){
                    return maybeSolution
                }
            }
        }
        return null
    }

    private fun Food.isSatisfiable(mapping: Map<Ingredient, Allergen>): Boolean {
        val expectedAllergens = this.allergens
        val detectedAllergens = this.ingredients.mapNotNull { mapping[it] }
        return detectedAllergens.containsAll(expectedAllergens) || detectedAllergens.size < ingredients.size
    }
}

