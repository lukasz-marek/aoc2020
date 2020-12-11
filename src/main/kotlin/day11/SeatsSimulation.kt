package day11

enum class Place {
    EMPTY, TAKEN, FLOOR
}

data class Grid(val layout: List<List<Place>>)

interface Simulator {
    fun runSimulations(grid: Grid): Grid
}

fun parseInput(input: List<String>): Grid =
    input.asSequence()
        .map { it.trim() }
        .map { it.toList() }
        .map { row ->
            row.map {
                when (it) {
                    'L' -> Place.EMPTY
                    '.' -> Place.FLOOR
                    '#' -> Place.TAKEN
                    else -> throw IllegalArgumentException("'$it' wasn't expected here")
                }
            }
        }
        .toList().let { Grid(it) }

open class SimulatorImpl : Simulator {
    protected open val threshold = 4

    final override tailrec fun runSimulations(grid: Grid): Grid {
        val newLayout = nextLayout(grid)
        return if (newLayout == grid.layout) grid else runSimulations(Grid(newLayout))
    }

    protected open fun Grid.countOccupiedNeighbours(row: Int, column: Int): Int {
        var occupiedNeighboursCount = 0
        for (neighbourRow in (row - 1)..(row + 1)) {
            for (neighbourColumn in (column - 1)..(column + 1)) {
                if (neighbourColumn != column || neighbourRow != row) {
                    if (neighbourRow in layout.indices && neighbourColumn in layout[neighbourRow].indices) {
                        if (layout[neighbourRow][neighbourColumn] == Place.TAKEN) {
                            occupiedNeighboursCount += 1
                        }
                    }
                }
            }
        }
        return occupiedNeighboursCount
    }

    private fun nextLayout(grid: Grid): List<List<Place>> {
        val newLayout = mutableListOf<MutableList<Place>>()
        for (row in grid.layout.indices) {
            newLayout.add(mutableListOf())
            for (column in grid.layout[row].indices) {
                val newPlace = when (val place = grid.layout[row][column]) {
                    Place.EMPTY -> {
                        val occupiedNeighbours = grid.countOccupiedNeighbours(row, column)
                        if (occupiedNeighbours == 0) Place.TAKEN else place
                    }
                    Place.TAKEN -> {
                        val occupiedNeighbours = grid.countOccupiedNeighbours(row, column)
                        if (occupiedNeighbours >= threshold) Place.EMPTY else place
                    }
                    else -> place
                }
                newLayout[row].add(newPlace)
            }
        }
        return newLayout
    }
}

@ExperimentalStdlibApi
class CorrectSimulatorImpl : SimulatorImpl() {
    override val threshold: Int = 5

    override fun Grid.countOccupiedNeighbours(row: Int, column: Int): Int {
        val center = Pair(row, column)
        val neighbourIndexes = buildList {
            for (rowStep in -1..1) {
                for (columnStep in -1..1) {
                    if (columnStep != 0 || rowStep != 0) {
                        val indexesToVisit = center
                            .coordinatesSequence(rowStep, columnStep)
                            .takeWhile { it.first in layout.indices && it.second in layout[it.first].indices }
                        add(indexesToVisit)
                    }
                }
            }
        }

        var occupiedNeighboursCount = 0
        sequencesLoop@ for (indexesSequence in neighbourIndexes) {
            for ((rowIndex, columnIndex) in indexesSequence) {
                if (layout[rowIndex][columnIndex] == Place.TAKEN) {
                    occupiedNeighboursCount += 1
                    continue@sequencesLoop
                } else if (layout[rowIndex][columnIndex] == Place.EMPTY) {
                    continue@sequencesLoop
                }
            }
        }

        return occupiedNeighboursCount
    }

    private fun Pair<Int, Int>.coordinatesSequence(rowStep: Int, columnStep: Int): Sequence<Pair<Int, Int>> {
        val rowsSequence = generateSequence(this.first) { it + rowStep }
        val colsSequence = generateSequence(this.second) { it + columnStep }
        return rowsSequence.zip(colsSequence).filter { it != this }
    }
}