package edu.asu.stratego.gui.handler;

import edu.asu.stratego.gui.core.SceneManager;
import edu.asu.stratego.gui.scene.MenuScene;

public class ResultActionHandler {

    public static void backToMenu() {
        SceneManager.switchScene(MenuScene.getScene());
    }

    public static void exitApp() {
        System.exit(0);
    }
}
