package edu.asu.stratego.gui.scene;

import edu.asu.stratego.game.client.ClientGameManager;
import edu.asu.stratego.gui.core.SceneManager;
import edu.asu.stratego.gui.stage.FormationStage;
import edu.asu.stratego.gui.stage.PauseStage;
import javafx.scene.Scene;

public class PauseScene {

    public static void show() {
        PauseStage pauseStage = new PauseStage(ClientGameManager.getConnectionManager());
        pauseStage.showAndWait();
    }
}
