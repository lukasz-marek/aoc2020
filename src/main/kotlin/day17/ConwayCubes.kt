package day17

inline class Cube(val isAlive: Boolean)
data class Grid2D(val cubes: List<List<Cube>>)
data class Grid3D(val sections: List<Grid2D>)
data class Coordinates(val row: Int, val column: Int, val section: Int)

fun Grid3D.get(coordinates: Coordinates): Cube =
    sections[coordinates.section].cubes[coordinates.row][coordinates.column]

fun Grid3D.neighbours(coordinates: Coordinates): List<Coordinates> {
    val neighbours = mutableListOf<Coordinates>()
    val section = coordinates.section
    val row = coordinates.row
    val column = coordinates.column
    for (currentSection in (section - 1)..(section + 1)) {
        if (currentSection in sections.indices) {
            for (currentRow in (row - 1)..(row + 1)) {
                if (currentRow in sections[currentSection].cubes.indices) {
                    for (currentColumn in (column - 1)..(column + 1)) {
                        if (currentColumn in sections[currentSection].cubes[currentRow].indices) {
                            if (currentRow != row || currentColumn != column || currentSection != section) {
                                neighbours.add(
                                    Coordinates(
                                        row = currentRow,
                                        column = currentColumn,
                                        section = currentSection
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    return neighbours
}

fun grid2DOf(rowsNumber: Int, columnsNumber: Int): Grid2D {
    val data = MutableList(rowsNumber) { MutableList(columnsNumber) { Cube(false) } }
    return Grid2D(data)
}

enum class ResizeDirection2D {
    UP, DOWN, LEFT, RIGHT
}

fun Grid2D.resizableDirections(): Set<ResizeDirection2D> {
    val resizeUp = cubes.first().any { it.isAlive }
    val resizeDown = cubes.last().any { it.isAlive }
    val resizeLeft = cubes.map { it.first() }.any { it.isAlive }
    val resizeRight = cubes.map { it.last() }.any { it.isAlive }

    val directions = mutableSetOf<ResizeDirection2D>()
    if (resizeUp) directions.add(ResizeDirection2D.UP)
    if (resizeDown) directions.add(ResizeDirection2D.DOWN)
    if (resizeLeft) directions.add(ResizeDirection2D.LEFT)
    if (resizeRight) directions.add(ResizeDirection2D.RIGHT)

    return directions
}

enum class ResizeDirection3D {
    FRONT, BACK
}

fun Grid3D.resizableDirections(): Set<ResizeDirection3D> {
    val resizeFront = sections.first().cubes.asSequence().flatten().any { it.isAlive }
    val resizeBack = sections.last().cubes.asSequence().flatten().any { it.isAlive }

    val directions = mutableSetOf<ResizeDirection3D>()
    if (resizeFront) directions.add(ResizeDirection3D.FRONT)
    if (resizeBack) directions.add(ResizeDirection3D.BACK)

    return directions
}

fun Grid2D.resized(directions: Set<ResizeDirection2D>): Grid2D {
    val mutableContent = cubes.map { it.toMutableList() }.toMutableList()
    for (direction in directions) {
        when (direction) {
            ResizeDirection2D.LEFT -> mutableContent.forEach { it.add(0, Cube(false)) }
            ResizeDirection2D.RIGHT -> mutableContent.forEach { it.add(Cube(false)) }
            ResizeDirection2D.UP -> mutableContent.add(0, MutableList(mutableContent.first().size) { Cube(false) })
            ResizeDirection2D.DOWN -> mutableContent.add(MutableList(mutableContent.last().size) { Cube(false) })
        }
    }
    return Grid2D(mutableContent)
}

fun Grid3D.adjusted(): Grid3D {
    val grid2DResizeDirections = sections.asSequence()
        .map { it.resizableDirections() }
        .reduce { acc, resizeDirections ->
            acc + resizeDirections
        }

    val mutableSections = sections.map { it.resized(grid2DResizeDirections) }.toMutableList()

    val grid3dResizeDirections = resizableDirections()
    for (direction in grid3dResizeDirections) {
        when (direction) {
            ResizeDirection3D.FRONT -> with(mutableSections.first().cubes) {
                mutableSections.add(0, grid2DOf(size, first().size))
            }
            ResizeDirection3D.BACK -> with(mutableSections.last().cubes) {
                mutableSections.add(grid2DOf(size, first().size))
            }
        }
    }
    return Grid3D(mutableSections)
}

fun Cube.nextValue(aliveNeighboursCount: Int): Cube {
    return when {
        isAlive -> Cube(aliveNeighboursCount in 2..3)
        else -> Cube(aliveNeighboursCount == 3)
    }
}

fun iterate(grid3D: Grid3D, times: Int): Grid3D {
    var currentGrid = grid3D.adjusted()
    for (iteration in 0 until times) {
        currentGrid.sections.flatMap { it.cubes.flatten() }.count { it.isAlive }.let { println("$it at $iteration") }
        val gridValues = List(currentGrid.sections.size) { sectionNumber ->
            List(currentGrid.sections[sectionNumber].cubes.size) { rowNumber ->
                List(currentGrid.sections[sectionNumber].cubes[rowNumber].size) { columnNumber ->
                    val currentCoordinates =
                        Coordinates(row = rowNumber, column = columnNumber, section = sectionNumber)
                    val currentValue = currentGrid.get(currentCoordinates)
                    val neighboursCoordinates = grid3D.neighbours(currentCoordinates)
                    currentValue.nextValue(neighboursCoordinates.map { currentGrid.get(it) }.count { it.isAlive })
                }
            }
        }
        currentGrid = Grid3D(gridValues.map { section -> Grid2D(section) }).adjusted()
    }
    return currentGrid
}

