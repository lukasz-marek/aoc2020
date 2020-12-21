package day21

import java.io.File

object Loader {

    fun load(): List<Food> {
        val inputFileUrl = this::class.java.classLoader.getResource("day21.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { parseFood(it) }.toList() }
    }
}

fun main() {
    val foods = Loader.load()
    val allIngredients = foods.flatMap { it.ingredients }.toSet()
    val allAllergens = foods.flatMap { it.allergens }.toSet()

    println("Info:")
    println("Foods: ${foods.size}")
    println("Ingredients: ${allIngredients.size}")
    println("Allergens: ${allAllergens.size}")

    val solver = SimpleRecursiveSolver()
    val assignments = solver.solve(foods)!!
    val ingredientsWithoutAllergens = assignments.filterValues { it == NoAllergen }.keys
    var result1 = 0
    for(ingredient in ingredientsWithoutAllergens){
        for(food in foods){
            if(food.ingredients.contains(ingredient)){
                result1 += 1
            }
        }
    }
    println("Part 1 result is $result1")
}