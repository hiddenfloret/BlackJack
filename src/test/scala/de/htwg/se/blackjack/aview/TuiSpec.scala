package de.htwg.se.blackjack.aview

import java.io.{ByteArrayOutputStream, StringReader}

import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.model.Hand
import de.htwg.se.blackjack.controller.GameState._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {
    "A Blackjack Tui" should {
        val controller = new Controller(new Hand(), new Hand())
        val tui = new Tui(controller)

        "create two new hands on input 'n'" in {
            tui.processInputLine("n")
            controller.playerHand.cards.size should be(2)
            controller.dealerHand.cards.size should be(2)
            controller.gameState should be(FirstRound)
        }

        "add a card to player's hand on input 'h'" in {
            tui.processInputLine("h")
            controller.playerHand.cards.size should be(3)
        }

        "add a card to dealer's hand on input 's'" in {
            tui.processInputLine("s")
            controller.dealerHand.cards.size should be(3)
        }

        "quit the game on input 'q'" in {
            tui.processInputLine("q")
            controller.gameState should be(EndGame)
        }

        "should have this output when unknown command" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                tui.processInputLine("this is a unknown command")
            }
            out.toString should include ("unknown command")
        }

        "should have this output when PlayerLost" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = PlayerLost
                controller.testNotify()
            }
            out.toString should include ("The Dealer has won!")
        }

        "should have this output when PlayerWon" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = PlayerWon
                controller.testNotify()
            }
            out.toString should include ("The Player has won!")
        }

        "should have this output when Draw" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = Draw
                controller.testNotify()
            }
            out.toString should include ("It's a draw!")
        }

        "should have this output when has a BlackJack in FirstRound" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = BlackJack
                controller.testNotify()
            }
            out.toString should include ("Lucky boy! The Player has won")
        }

        "should have this output when is WrongCmd" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = WrongCmd
                controller.testNotify()
            }
            out.toString should include ("Command not allowed!")
        }

        "should have this output when is EndGame" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = EndGame
                controller.testNotify()
            }
            out.toString should include ("Good bye!")
        }

        "should have this output when is PlayersTurn" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = PlayersTurn
                controller.testNotify()
            }
            out.toString should include ("It's your turn. Hit or stand?(h/s)")
        }
    }
}