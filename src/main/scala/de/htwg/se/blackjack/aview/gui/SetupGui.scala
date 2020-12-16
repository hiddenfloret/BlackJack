package de.htwg.se.blackjack.aview.gui

import scala.swing._
import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.controller.GameState._

import scala.swing.event.ButtonClicked

class SetupGui(controller: Controller) extends Frame {
    peer.setLocationRelativeTo(null)
    title = "Blackjack"

    val btn_undo = new Button {
        text = "Undo"
    }
    val btn_redo = new Button {
        text = "Redo"
    }
    val btn_do = new Button {
        text = "Do"
    }

    val flowPanel = new FlowPanel {
        contents += btn_undo
        contents += btn_redo
        contents += btn_do
    }

    val txt_playername = new TextField {
        columns = 40
    }

    val lbl_playername = new Label {
        text = controller.getPlayerName
    }

    contents = new GridPanel(3,1) {
        contents += lbl_playername
        contents += txt_playername
        contents += flowPanel
        listenTo(btn_undo, btn_do)

        reactions += {
            case ButtonClicked(component) => {
                if(component == btn_undo) {
                    controller.undo
                } else if(component == btn_redo) {
                    controller.redo
                } else if(component == btn_do) {
                    controller.performSetPlayerName(txt_playername.text)
                }

                if (controller.gameState == PLAYER_TURN) {
                    peer.setVisible(false)
                }

                lbl_playername.text = controller.getPlayerName
                txt_playername.text = ""
            }
        }
    }
}
