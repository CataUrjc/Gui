package edu.asu.stratego.gui.scene;

import edu.asu.stratego.gui.stage.FormationStage;

public class FormationScene {

    public static void show() {
        //Scene scene = FormationSceneBuilder.build(Game.getBoard(), Game.getPlayer().getColor());

        FormationStage formationStage = new FormationStage();
        formationStage.showAndWait();
    }
}
