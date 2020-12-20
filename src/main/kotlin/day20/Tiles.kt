package day20

data class Tile(val id: Int, val content: List<List<Char>>)

private val Tile.edges: List<List<Char>>
    get() {
        val upper = content.first()
        val lower = content.last()
        val left = content.map { it.first() }
        val right = content.map { it.last() }
        return listOf(upper, lower, left, right)
    }

fun Tile.isAdjacentTo(other: Tile): Boolean {
    if (other.id == id) {
        return false
    }
    val otherEdges = other.edges
    return edges.any { thisEdge -> otherEdges.any { it == thisEdge || it.reversed() == thisEdge } }
}

fun findCorners(tiles: List<Tile>): List<Tile> {
    return tiles.filter { tile -> tiles.count { tile.isAdjacentTo(it) } == 2 }
}

private val tileIdPattern = Regex("^Tile (\\d+):$")
private val tilePattern = Regex("^([\\.\\#]){10}$")
fun parseTile(tile: List<String>): Tile {
    val (tileId, _) = tileIdPattern.matchEntire(tile.first())!!.destructured
    val content = tile.drop(1).onEach { assert(tilePattern.matches(it)) }.map { it.toList() }
    return Tile(tileId.toInt(), content)
}