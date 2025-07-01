package edu.asu.stratego.gui.board;

import edu.asu.stratego.game.board.ClientBoard;
import edu.asu.stratego.game.board.ClientSquare;
import edu.asu.stratego.game.models.*;
import edu.asu.stratego.gui.setup.SetupPanel;
import edu.asu.stratego.gui.setup.SetupPieces;
import edu.asu.stratego.util.HashTables;

import java.util.*;

public class FormationManager {

    /**
     * Enumerates the available predefined formation types.
     */
    public enum FormationType {
        /**
         * Represents an offensive piece formation.
         */
        OFFENSIVE,
        /**
         * Represents a defensive piece formation.
         */
        DEFENSIVE,
        /**
         * Represents a balanced piece formation.
         */
        BALANCED
    }

    /**
     * Applies a selected formation to the player's starting area on the board.
     *
     * @param board  The client's game board.
     * @param player The local player.
     * @param type   The type of formation to apply (OFFENSIVE, DEFENSIVE, BALANCED).
     */
    public void applyFormation(ClientBoard board, Player player, FormationType type) {
        switch (type) {
            case OFFENSIVE -> applyOffensiveFormation();
            case DEFENSIVE -> applyDefensiveFormation();
            case BALANCED -> applyBalancedFormation();
        }
    }

    /**
     * Places a given formation of pieces onto the board at a specified starting row.
     *
     * @param formation   A 2D list representing the piece types for the formation.
     * @param startRow    The row on the board where the formation should start being placed.
     * @param board       The client's game board.
     * @param playerColor The color of the player placing the pieces.
     */
    public static void placePieces(List<List<PieceType>> formation, int startRow, ClientBoard board, PieceColor playerColor) {
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
        SetupPanel.finishSetup();
    }

    /**
     * Applies a predefined offensive formation to the player's starting area on the board.
     */
    public static void applyOffensiveFormation() {
        ClientBoard board = Game.getBoard();
        PieceColor playerColor = Game.getPlayer().getColor();
        int startRow = 6;

        List<List<PieceType>> formation = Arrays.asList(
                Arrays.asList(PieceType.BOMB, PieceType.BOMB, PieceType.BOMB, PieceType.FLAG, PieceType.BOMB,
                        PieceType.COLONEL, PieceType.MAJOR, PieceType.SERGEANT, PieceType.SERGEANT, PieceType.MARSHAL),
                Arrays.asList(PieceType.CAPTAIN, PieceType.CAPTAIN, PieceType.CAPTAIN, PieceType.LIEUTENANT,
                        PieceType.LIEUTENANT, PieceType.MAJOR, PieceType.MAJOR, PieceType.COLONEL, PieceType.GENERAL,
                        PieceType.SCOUT),
                Arrays.asList(PieceType.MINER, PieceType.MINER, PieceType.MINER, PieceType.SCOUT, PieceType.SCOUT,
                        PieceType.SCOUT, PieceType.LIEUTENANT, PieceType.LIEUTENANT, PieceType.SERGEANT, PieceType.CAPTAIN),
                Arrays.asList(PieceType.BOMB, PieceType.SCOUT, PieceType.SCOUT, PieceType.SPY, PieceType.MINER,
                        PieceType.MINER, PieceType.SERGEANT, PieceType.SCOUT, PieceType.SCOUT, PieceType.BOMB)
        );
        placePieces(formation, startRow, board, playerColor);
    }

    /**
     * Applies a predefined defensive formation to the player's starting area on the board.
     */
    public static void applyDefensiveFormation() {
        ClientBoard board = Game.getBoard();
        PieceColor playerColor = Game.getPlayer().getColor();
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

    /**
     * Applies a predefined balanced formation to the player's starting area on the board.
     */
    public static void applyBalancedFormation() {
        ClientBoard board = Game.getBoard();
        PieceColor playerColor = Game.getPlayer().getColor();
        int startRow = 6;

        List<List<PieceType>> formation = Arrays.asList(
                Arrays.asList(PieceType.SCOUT, PieceType.SCOUT, PieceType.SCOUT, PieceType.SCOUT, PieceType.SCOUT,
                        PieceType.SCOUT, PieceType.SCOUT, PieceType.SCOUT, PieceType.MINER, PieceType.MAJOR),
                Arrays.asList(PieceType.CAPTAIN, PieceType.CAPTAIN, PieceType.CAPTAIN, PieceType.MAJOR,
                        PieceType.MAJOR, PieceType.COLONEL, PieceType.GENERAL, PieceType.LIEUTENANT, PieceType.LIEUTENANT,
                        PieceType.MINER),
                Arrays.asList(PieceType.BOMB, PieceType.BOMB, PieceType.MINER, PieceType.MINER, PieceType.SERGEANT,
                        PieceType.SERGEANT, PieceType.COLONEL, PieceType.COLONEL, PieceType.SPY, PieceType.SERGEANT),
                Arrays.asList(PieceType.BOMB, PieceType.FLAG, PieceType.BOMB, PieceType.BOMB, PieceType.BOMB,
                        PieceType.COLONEL, PieceType.LIEUTENANT, PieceType.MINER, PieceType.SERGEANT, PieceType.MARSHAL)
        );

        placePieces(formation, startRow, board, playerColor);
    }

    /**
     * Randomly sets up the player's pieces in their starting area on the board.
     */
    public static void randomSetup() {
        PieceColor playerColor = Game.getPlayer().getColor();
        ClientBoard board = Game.getBoard();
        Random random = new Random();
        List<PieceType> availablePieceTypes = new ArrayList<>(Arrays.asList(PieceType.values()));

        for (int col = 0; col < 10; ++col) {
            for (int row = 6; row < 10; ++row) {
                ClientSquare square = board.getSquare(row, col);
                if (square.getPiece() == null) {
                    PieceType pieceType = getRandomAvailablePieceType(availablePieceTypes, random);
                    if (pieceType != null) {
                        square.setPiece(new Piece(pieceType, playerColor, false));
                        square.getPiecePane().setPiece(HashTables.PIECE_MAP.get(square.getPiece().getPieceSpriteKey()));
                        SetupPieces.decrementPieceCount(pieceType);
                    }
                }
            }
        }
        SetupPanel.finishSetup();
    }

    /**
     * Retrieves a random available piece type from the list of available pieces.
     *
     * @param availableTypes A list of all possible piece types.
     * @param random         A Random number generator.
     * @return A randomly selected {@code PieceType} that still has available count in {@link SetupPieces},
     * or {@code null} if no pieces are available.
     */
    private static PieceType getRandomAvailablePieceType(List<PieceType> availableTypes, Random random) {
        List<PieceType> stillAvailable = new ArrayList<>(availableTypes);
        while (!stillAvailable.isEmpty()) {
            int randomIndex = random.nextInt(stillAvailable.size());
            PieceType potentialType = stillAvailable.get(randomIndex);
            if (SetupPieces.getPieceCount(potentialType) > 0) {
                return potentialType;
            } else {
                stillAvailable.remove(randomIndex);
            }
        }
        return null;
    }
}