package edu.asu.stratego.gui.board.indicator;

import edu.asu.stratego.gui.board.BoardTurnIndicator;
import javafx.scene.shape.Rectangle;

public class TurnIndicatorInitializer {

    public static Rectangle initialize() {
        new BoardTurnIndicator(); // inicia hilo
        return BoardTurnIndicator.getTurnIndicator();
    }
}