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

class SimulatorImpl : Simulator {
    override tailrec fun runSimulations(grid: Grid): Grid {
        val newLayout = nextLayout(grid)
        return if (newLayout == grid.layout) grid else runSimulations(Grid(newLayout))
    }

    private fun Grid.getNeighbours(row: Int, column: Int): List<Place> {
        val neighbours = mutableListOf<Place>()
        for (neighbourRow in (row - 1)..(row + 1)) {
            for (neighbourColumn in (column - 1)..(column + 1)) {
                if (neighbourColumn != column || neighbourRow != row) {
                    if (neighbourRow in layout.indices && neighbourColumn in layout[neighbourRow].indices) {
                        neighbours.add(layout[neighbourRow][neighbourColumn])
                    }
                }
            }
        }
        return neighbours
    }

    private fun nextLayout(grid: Grid): List<List<Place>> {
        val newLayout = mutableListOf<MutableList<Place>>()
        for (row in grid.layout.indices) {
            newLayout.add(mutableListOf())
            for (column in grid.layout[row].indices) {
                val newPlace = when (val place = grid.layout[row][column]) {
                    Place.EMPTY -> {
                        val occupiedNeighbours = grid.getNeighbours(row, column).count { it == Place.TAKEN }
                        if (occupiedNeighbours == 0) Place.TAKEN else place
                    }
                    Place.TAKEN -> {
                        val occupiedNeighbours = grid.getNeighbours(row, column).count { it == Place.TAKEN }
                        if (occupiedNeighbours >= 4) Place.EMPTY else place
                    }
                    else -> place
                }
                newLayout[row].add(newPlace)
            }
        }
        return newLayout
    }
}