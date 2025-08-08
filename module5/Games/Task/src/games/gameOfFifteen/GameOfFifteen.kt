package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import board.getNeighbour
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = object: Game {
    private val gameBoard = createGameBoard<Int>(4)

    override fun initialize() {
        gameBoard
            .getAllCells()
            .zip(initializer.initialPermutation)
            .forEach { (cell, value) -> gameBoard[cell] = value }
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        val values = gameBoard.getAllCells().mapNotNull { gameBoard[it] }
        return values.sorted() == values
    }

    override fun processMove(direction: Direction) {
        val cannotMoveCells = when (direction) {
            Direction.UP -> gameBoard.getRow(1, 1..4)
            Direction.DOWN -> gameBoard.getRow(4, 1..4)
            Direction.RIGHT -> gameBoard.getColumn( 1..4, 4)
            Direction.LEFT -> gameBoard.getColumn( 1..4, 1)
        }

        val cell = gameBoard.getAllCells()
            .filter { !cannotMoveCells.contains(it) }
            .find {
                val neighbour = gameBoard.getNeighbour(it, direction)
                if (neighbour == null) {
                    false
                } else {
                    gameBoard[neighbour] == null
                }
            }

        if (cell == null) return

        val emptyCell = gameBoard.getNeighbour(cell, direction)

        if (emptyCell == null) return

        gameBoard[emptyCell] = gameBoard[cell]
        gameBoard[cell] = null
    }

    override fun get(i: Int, j: Int): Int? {
        return gameBoard[Cell(i, j)]
    }
}
