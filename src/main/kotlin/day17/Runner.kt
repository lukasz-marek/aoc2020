package day17

import java.io.File


object Loader {

    fun load(): Grid3D {
        val inputFileUrl = this::class.java.classLoader.getResource("day17.txt")
        val data = File(inputFileUrl.toURI())
            .useLines { lines ->
                lines.map {
                    it.trim().map { character ->
                        when (character) {
                            '#' -> Cube(true)
                            else -> Cube(false)
                        }
                    }.toMutableList()
                }.toMutableList()
            }
        return Grid3D(mutableListOf(Grid2D(data)))
    }
}

fun main() {
    val mutableInput = Loader.load()
    val result = iterate(mutableInput, 600).let {
        it.sections.flatMap { section -> section.cubes.flatten() }.count { cube -> cube.isAlive }
    }
    println("$result")
}