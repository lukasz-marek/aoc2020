package day20

import java.io.File

object Loader {

    fun load(): List<Tile> {
        val inputFileUrl = this::class.java.classLoader.getResource("day20.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.filter { it.isNotEmpty() }.chunked(11).map { parseTile(it) }.toList() }
    }
}

fun main() {
    val tiles = Loader.load()
    val corners = findCorners(tiles)
    println(corners.map { it.id.toLong() }.reduce { acc, i -> acc * i })
}