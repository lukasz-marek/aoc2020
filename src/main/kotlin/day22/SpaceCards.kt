package day22

data class Player(val name: String, val deck: List<Card>)
data class Card(val value: Int) : Comparable<Card> {
    override fun compareTo(other: Card): Int = value.compareTo(other.value)
}

tailrec fun playGame(player1: Player, player2: Player): Player {
    if (player1.deck.isEmpty()) {
        return player2
    } else if (player2.deck.isEmpty()) {
        return player1
    }

    val player1Card = player1.deck.first()
    val player2Card = player2.deck.first()
    return when {
        player1Card > player2Card -> {
            val newPlayer1Deck = player1.deck.drop(1) + player1Card + player2Card
            val newPlayer2Deck = player2.deck.drop(1)
            playGame(player1.copy(deck = newPlayer1Deck), player1.copy(deck = newPlayer2Deck))
        }
        player2Card > player1Card -> {
            val newPlayer1Deck = player1.deck.drop(1)
            val newPlayer2Deck = player2.deck.drop(1) + player2Card + player1Card
            playGame(player1.copy(deck = newPlayer1Deck), player1.copy(deck = newPlayer2Deck))
        }
        else -> {
            throw IllegalStateException("Tie!")
        }
    }
}

fun calculateScore(deck: List<Card>): Long =
    deck.reversed()
        .asSequence()
        .map { it.value.toLong() }
        .withIndex()
        .map { (index, value) -> (index + 1) * value }
        .sum()