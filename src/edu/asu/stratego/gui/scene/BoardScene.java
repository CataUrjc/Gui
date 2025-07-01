package edu.asu.stratego.gui.scene;

import edu.asu.stratego.gui.board.BoardInitializer;
import edu.asu.stratego.gui.board.BoardPieceResizer;
import edu.asu.stratego.gui.board.BoardSceneAssembler;
import edu.asu.stratego.gui.core.ClientStageInitializer;
import edu.asu.stratego.gui.util.UIScale;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class BoardScene {

    private static Scene scene = null;
    private static StackPane root = new StackPane();
    public BoardScene() {
        BoardInitializer.initializeTurnIndicator();
        BoardPieceResizer.resize();
        root = BoardSceneAssembler.assemble();

        this.scene = new Scene(root, UIScale.getSide(), UIScale.getSide());
    }

    public static Scene getScene() {
        return scene;
    }

    public static StackPane getRootPane() {
        return root;
    }
}
