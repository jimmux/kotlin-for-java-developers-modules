package board

import board.Direction.*

fun SquareBoard.getNeighbour(cell: Cell, direction: Direction): Cell? = when (direction) {
    UP -> this.getCellOrNull(cell.i - 1, cell.j)
    DOWN -> this.getCellOrNull(cell.i + 1, cell.j)
    RIGHT -> this.getCellOrNull(cell.i, cell.j + 1)
    LEFT -> this.getCellOrNull(cell.i, cell.j - 1)
}

fun createSquareBoard(width: Int): SquareBoard = object: SquareBoard {
    override val width: Int = width
    var cells = emptySet<Cell>()

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return when {
            i !in 1..width -> null
            j !in 1.. width -> null
            else -> {
                var cell = cells.find { it.i == i && it.j == j }
                if (cell == null) {
                    cell = Cell(i, j)
                    cells += cell
                    cell
                } else cell
            }
        }
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> =
        (1..width).flatMap { i ->
            (1..width).map { j -> getCell(i, j) }
        }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = jRange
        .filter { it in 1..width }
        .map { getCell(i, it) }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = iRange
        .filter { it in 1..width }
        .map { getCell(it, j) }

    override fun Cell.getNeighbour(direction: Direction): Cell? = getCellNeighbour(this, direction)

    private fun getCellNeighbour(cell: Cell, direction: Direction) = this.getNeighbour(cell, direction)
}

fun <T> createGameBoard(width: Int): GameBoard<T> = object: GameBoard<T> {
    val square = createSquareBoard(width)
    override val width
        get() = square.width

    private val content: MutableMap<Cell, T> = mutableMapOf()

    override fun get(cell: Cell): T? {
        val validCell = square.getCellOrNull(cell.i, cell.j)
        if (validCell == null) {
            return null
        }
        return content[validCell]
    }

    override fun set(cell: Cell, value: T?) {
        if (value == null) {
            content.remove(cell)
        } else {
            content.put(cell, value)
        }
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = square
        .getAllCells()
        .filter { predicate(get(it)) }

    override fun find(predicate: (T?) -> Boolean): Cell? = square
        .getAllCells()
        .find { predicate(get(it)) }

    override fun any(predicate: (T?) -> Boolean): Boolean = square
        .getAllCells()
        .any { predicate(get(it)) }

    override fun all(predicate: (T?) -> Boolean): Boolean = square
        .getAllCells()
        .all { predicate(get(it)) }

    // I feel like there's a better way to do this...
    override fun getCellOrNull(i: Int, j: Int): Cell? = square.getCellOrNull(i, j)
    override fun getCell(i: Int, j: Int): Cell = square.getCell(i, j)
    override fun getAllCells(): Collection<Cell> = square.getAllCells()
    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = square.getRow(i, jRange)
    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = square.getColumn(iRange, j)
    override fun Cell.getNeighbour(direction: Direction): Cell? = square.getNeighbour(this, direction)
}
