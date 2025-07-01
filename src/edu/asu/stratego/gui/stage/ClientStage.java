package edu.asu.stratego.gui.stage;

import java.awt.Dimension;
import java.awt.Toolkit;

import edu.asu.stratego.game.models.GameStatus;
import edu.asu.stratego.gui.scene.ConnectionScene;
import edu.asu.stratego.gui.scene.EndGameScene;
import edu.asu.stratego.gui.scene.WaitingScene;
import edu.asu.stratego.gui.scene.BoardScene;
import javafx.stage.Stage;

public class ClientStage extends Stage {

    private ConnectionScene connectionScene;
    private WaitingScene waitingScene;
    private BoardScene boardScene;
    private EndGameScene endGameScene;

    private static final double UNIT;
    private static int SIDE = 0;

    // Static block to calculate UI dimensions based on screen size
    static {
        int side1;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Calculate SIDE to be a multiple of 12 for consistent unit size
        side1 = (int) (0.85 * screenSize.getHeight());
        SIDE = (side1 / 12) * 12;
        UNIT = SIDE / 12.0;
    }

    /**
     * Initializes the main stage, sets the title, makes it non-resizable,
     * and displays the connection scene.
     */
    public ClientStage() {
        setTitle("ASU Stratego");
        setResizable(false);
        setConnectionScene();
        show();
    }

    /**
     * Sets and displays the connection scene. If the scene is not yet initialized,
     * it creates a new instance.
     */
    public void setConnectionScene() {
        if (connectionScene == null) {
            connectionScene = new ConnectionScene();
        }
        setScene(connectionScene.getScene());
    }

    /**
     * Sets and displays the waiting scene. If the scene is not yet initialized,
     * it creates a new instance.
     */
    public void setWaitingScene() {
        if (waitingScene == null) {
            waitingScene = new WaitingScene();
        }
        setScene(waitingScene.getScene());
    }

    /**
     * Sets and displays the end game scene based on the provided game status.
     * If the scene is not yet initialized or if a new status needs to be displayed,
     * it creates a new instance.
     *
     * @param status The {@code GameStatus} indicating the outcome of the game.
     */
    public void setEndGameScene(GameStatus status) {
        endGameScene = new EndGameScene(status);
        setScene(endGameScene.getScene());
    }

    /**
     * Sets and displays the main board scene. If the scene is not yet initialized,
     * it creates a new instance.
     */
    public void setBoardScene() {
        if (boardScene == null) {
            boardScene = new BoardScene();
        }
        setScene(boardScene.getScene());
    }

    /**
     * Returns the current connection scene.
     *
     * @return The current {@code ConnectionScene}.
     */
    public ConnectionScene getConnectionScene() {
        return connectionScene;
    }

    /**
     * Returns the base graphical unit, which is 1/12th of the board's side length.
     * This unit is used for scaling UI elements relative to the board size.
     *
     * @return The base graphical unit.
     */
    public static double getUnit() {
        return UNIT;
    }

    /**
     * Returns the total side length of the game board in pixels.
     *
     * @return The side length of the board.
     */
    public static int getSide() {
        return SIDE;
    }
}