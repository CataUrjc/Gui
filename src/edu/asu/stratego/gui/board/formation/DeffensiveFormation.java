package edu.asu.stratego.gui.board.formation;

import edu.asu.stratego.game.models.PieceColor;
import edu.asu.stratego.game.models.Piece;
import edu.asu.stratego.game.models.PieceType;
import edu.asu.stratego.game.board.ClientBoard;
import edu.asu.stratego.game.board.ClientSquare;
import edu.asu.stratego.gui.setup.SetupPanel;
import edu.asu.stratego.util.HashTables;

import java.util.Arrays;
import java.util.List;

public class DeffensiveFormation implements FormationStrategy {
    @Override
    public void placePieces(List<List<PieceType>> formation, int startRow, ClientBoard board, PieceColor playerColor) {
        for (int i = 0; i < formation.size(); i++) {
            List<PieceType> row = formation.get(i);
            for (int j = 0; j < row.size(); j++) {
                PieceType pieceType = row.get(j);
                ClientSquare square = board.getSquare(startRow + i, j);
                Piece piece = new Piece(pieceType, playerColor, false);
                square.setPiece(piece);
                square.getPiecePane().setPiece(HashTables.PIECE_MAP.get(piece.getPieceSpriteKey()));
            }
        }
        System.out.println("He llegado hasta aqui 4." + board + playerColor);
        SetupPanel.finishSetup();
    }
    @Override
    public void apply(ClientBoard board, PieceColor playerColor) {
        System.out.println("He llegado hasta aqui 3." + board + playerColor);
        int startRow = 6;

        List<List<PieceType>> formation = Arrays.asList(
                Arrays.asList(PieceType.SCOUT, PieceType.SCOUT, PieceType.SCOUT, PieceType.SCOUT, PieceType.SCOUT,
                        PieceType.SCOUT, PieceType.SCOUT, PieceType.SCOUT, PieceType.MINER, PieceType.MINER),
                Arrays.asList(PieceType.CAPTAIN, PieceType.CAPTAIN, PieceType.LIEUTENANT, PieceType.MAJOR,
                        PieceType.MAJOR, PieceType.COLONEL, PieceType.COLONEL, PieceType.GENERAL, PieceType.LIEUTENANT,
                        PieceType.MAJOR),
                Arrays.asList(PieceType.BOMB, PieceType.BOMB, PieceType.MINER, PieceType.MINER, PieceType.SERGEANT,
                        PieceType.SERGEANT, PieceType.LIEUTENANT, PieceType.LIEUTENANT, PieceType.CAPTAIN, PieceType.CAPTAIN),
                Arrays.asList(PieceType.BOMB, PieceType.BOMB, PieceType.BOMB, PieceType.FLAG, PieceType.BOMB,
                        PieceType.SPY, PieceType.MINER, PieceType.SERGEANT, PieceType.SERGEANT, PieceType.MARSHAL)
        );

        placePieces(formation, startRow, board, playerColor);
    }
}
