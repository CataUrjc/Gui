package edu.asu.stratego.gui.handler;

import edu.asu.stratego.game.models.PieceColor;
import edu.asu.stratego.game.board.ClientBoard;
import edu.asu.stratego.gui.core.SceneManager;
import edu.asu.stratego.gui.setup.FormationManager;

public class FormationActionHandler {

    public static void applyOffensive(ClientBoard board, PieceColor color) {
        System.out.println("He llegado hasta aqui 1.");
        FormationManager.applyOffensiveFormation(board, color);
//        SceneManager.switchScene(BoardScene.getScene());
    }

    public static void applyDefensive(ClientBoard board, PieceColor color) {
        System.out.println("He llegado hasta aqui 1. BOARD y COLOR: " + board + color);
        FormationManager.applyDefensiveFormation(board, color);
//        SceneManager.closeStage();
    }

    public static void applyBalanced(ClientBoard board, PieceColor color) {
        System.out.println("He llegado hasta aqui 1.");
        FormationManager.applyBalancedFormation(board, color);
//        SceneManager.switchScene(BoardScene.getScene());
    }

    public static void closeFormationScene() {
        SceneManager.closeStage();
    }
}
