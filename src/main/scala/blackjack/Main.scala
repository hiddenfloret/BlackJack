package blackjack

object Main {
    def main(args:Array[String]): Unit = {
        var board = createGameBoard()
        println(board)
    }

    def createGameBoard(): String = {
        var board = ""
        val playerHand = Array("J", "2")
        val dealerHand = Array("7", "Q")
        val player = Person("Player", playerHand)
        val dealer = Person("Dealer", dealerHand)

        board = board.concat("Welcome to blackjack!\n")
        board = board.concat(player.toString())
        board = board.concat(dealer.toString())
        board = board.concat("It is Player's turn! hit or stand? (h/s): ")
        return board
    }
}