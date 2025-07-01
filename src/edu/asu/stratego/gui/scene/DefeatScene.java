package edu.asu.stratego.gui.scene;

import edu.asu.stratego.gui.core.SceneManager;

public class DefeatScene {

    public static void show() {
        SceneManager.switchScene(ResultSceneBuilder.build("Has perdido"));
    }
}
