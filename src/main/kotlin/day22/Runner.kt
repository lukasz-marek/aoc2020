package day22

import java.io.File

object Loader {

    fun loadPlayer1(): Player {
        val inputFileUrl = this::class.java.classLoader.getResource("day22_player1.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { Card(it.trim().toInt()) }.toList().let { Player("player1", it) } }
    }

    fun loadPlayer2(): Player {
        val inputFileUrl = this::class.java.classLoader.getResource("day22_player2.txt")
        return File(inputFileUrl.toURI())
            .useLines { lines -> lines.map { Card(it.trim().toInt()) }.toList().let { Player("player2", it) } }
    }
}

fun main() {
    val player1 = Loader.loadPlayer1()
    val player2 = Loader.loadPlayer2()
    val winner = playGame(player1, player2)
    println("Winner is ${winner.name} with score ${calculateScore(winner.deck)}")
}