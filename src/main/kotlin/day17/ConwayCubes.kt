package day17

inline class Cube(val isAlive: Boolean)
data class Grid2D(val cubes: MutableList<MutableList<Cube>>)
data class Grid3D(val sections: MutableList<Grid2D>)

fun Grid3D.get(row: Int, column: Int, section: Int): Cube = sections[section].cubes[row][column]

fun Grid3D.neighbours(row: Int, column: Int, section: Int): List<Cube> {
    val neighbours = mutableListOf<Cube>()
    for (currentSection in (section - 1)..(section + 1)) {
        if (currentSection in sections.indices) {
            for (currentRow in (row - 1)..(row + 1)) {
                if (currentRow in sections[currentSection].cubes.indices) {
                    for (currentColumn in (column - 1)..(column + 1)) {
                        if (currentColumn in sections[currentSection].cubes[currentRow].indices) {
                            if (currentRow != row || currentColumn != column || currentSection != section) {
                                neighbours.add(get(currentRow, currentColumn, currentSection))
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

fun Grid2D.resize(directions: Set<ResizeDirection2D>) {
    for (direction in directions) {
        when (direction) {
            ResizeDirection2D.LEFT -> cubes.forEach { it.add(0, Cube(false)) }
            ResizeDirection2D.RIGHT -> cubes.forEach { it.add(Cube(false)) }
            ResizeDirection2D.UP -> cubes.add(0, MutableList(cubes.first().size) { Cube(false) })
            ResizeDirection2D.DOWN -> cubes.add(MutableList(cubes.last().size) { Cube(false) })
        }
    }
}

fun Grid3D.adjustSize() {
    val grid2DResizeDirections = sections.asSequence()
        .map { it.resizableDirections() }
        .reduce { acc, resizeDirections ->
            acc + resizeDirections
        }

    sections.forEach { it.resize(grid2DResizeDirections) }

    val grid3dResizeDirections = resizableDirections()
    for (direction in grid3dResizeDirections) {
        when (direction) {
            ResizeDirection3D.FRONT -> with(sections.first().cubes) {
                sections.add(0, grid2DOf(size, first().size))
            }
            ResizeDirection3D.BACK -> with(sections.last().cubes) {
                sections.add(grid2DOf(size, first().size))
            }
        }
    }
}

fun Cube.nextValue(neighbours: List<Cube>): Cube {
    return when {
        isAlive -> Cube(neighbours.count { it.isAlive } in 2..3)
        else -> Cube(neighbours.count { it.isAlive } == 3)
    }
}

fun iterate(grid3D: Grid3D, times: Int): Grid3D {
    var currentGrid = grid3D
    for (iteration in 0 until times) {
        currentGrid.sections.flatMap { it.cubes.flatten() }.count { it.isAlive }.let { println("$it at $iteration") }
        currentGrid.adjustSize()
        val gridValues = MutableList(currentGrid.sections.size) { sectionNumber ->
            MutableList(currentGrid.sections[sectionNumber].cubes.size) { rowNumber ->
                MutableList(currentGrid.sections[sectionNumber].cubes[rowNumber].size) { columnNumber ->
                    val currentValue = currentGrid.get(row = rowNumber, column = columnNumber, section = sectionNumber)
                    val neighbours = grid3D.neighbours(row = rowNumber, column = columnNumber, section = sectionNumber)
                    currentValue.nextValue(neighbours)
                }
            }
        }
        currentGrid = Grid3D(gridValues.map { section -> Grid2D(section) }.toMutableList())
    }
    return currentGrid
}

