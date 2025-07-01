package edu.asu.stratego.gui.board;

import edu.asu.stratego.game.models.Game;
import edu.asu.stratego.gui.util.UIScale;

public class BoardPieceResizer {

    private static final double UNIT = UIScale.getUnit();

    public static void resize() {
        final int SIZE = 10;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                var square = Game.getBoard().getSquare(row, col);
                square.getPiecePane().getPiece().setFitHeight(UNIT);
                square.getPiecePane().getPiece().setFitWidth(UNIT);
                square.getEventPane().getHover().setFitHeight(UNIT);
                square.getEventPane().getHover().setFitWidth(UNIT);
            }
        }
    }
}
