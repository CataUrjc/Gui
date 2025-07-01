package edu.asu.stratego.gui.board.formation;

import edu.asu.stratego.game.models.PieceColor;
import edu.asu.stratego.game.models.PieceType;
import edu.asu.stratego.game.board.ClientBoard;

import java.util.List;

public interface FormationStrategy {
    void apply(ClientBoard board, PieceColor playerColor);

    void placePieces(List<List<PieceType>> formation, int startRow, ClientBoard board, PieceColor playerColor);
}
