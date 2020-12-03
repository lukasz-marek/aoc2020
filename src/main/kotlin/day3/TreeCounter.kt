package day3

enum class Field {
    TREE,
    EMPTY
}

data class Grid(val map: List<List<Field>>)

interface TreeCounter {
    fun countTrees(grid: Grid): Long
}

class TreeCounterImpl(private val stepsRight: Int, private val stepsDown: Int) : TreeCounter {

    override fun countTrees(grid: Grid): Long {
        var currentPosition = 0 to 0
        var treesCount = 0L

        while (currentPosition.first in grid.map.indices) {
            treesCount += when (grid.map[currentPosition.first][currentPosition.second]) {
                Field.TREE -> 1
                else -> 0
            }
            currentPosition = calculateNextPosition(currentPosition, grid)
        }

        return treesCount
    }

    private fun calculateNextPosition(currentPosition: Pair<Int, Int>, grid: Grid): Pair<Int, Int> {
        val currentRowSize = grid.map[currentPosition.first].size

        return with(currentPosition) {
            val nextRowIndex = first + stepsDown
            val nextColumnIndex = (second + stepsRight) % currentRowSize
            nextRowIndex to nextColumnIndex
        }
    }
}