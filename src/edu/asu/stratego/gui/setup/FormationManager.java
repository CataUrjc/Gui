package edu.asu.stratego.gui.setup;

import edu.asu.stratego.game.models.PieceColor;
import edu.asu.stratego.game.board.ClientBoard;
import edu.asu.stratego.gui.board.formation.BalancedFormation;
import edu.asu.stratego.gui.board.formation.DeffensiveFormation;
import edu.asu.stratego.gui.board.formation.OffensiveFormation;

public class FormationManager {

    public static void applyOffensiveFormation(ClientBoard board, PieceColor color) {
        System.out.println("He llegado hasta aqui 2. BOARD: " + board + color);
        new OffensiveFormation().apply(board, color);
    }

    public static void applyDefensiveFormation(ClientBoard board, PieceColor color) {
        System.out.println("He llegado hasta aqui 2. BOARD: " + board + color);
        new DeffensiveFormation().apply(board, color);
    }

    public static void applyBalancedFormation(ClientBoard board, PieceColor color) {
        new BalancedFormation().apply(board, color);
    }
}
