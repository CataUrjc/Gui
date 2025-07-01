package edu.asu.stratego.gui.scene;

import edu.asu.stratego.gui.component.ButtonFactory;
import edu.asu.stratego.gui.core.SceneManager;
import edu.asu.stratego.gui.util.UIScale;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PauseSceneBuilder {

    public static Scene build() {
        Button resumeButton = ButtonFactory.create("Reanudar", event ->
                SceneManager.switchScene(SceneManager.getInitialScene()),
                null
        );

        Button quitButton = ButtonFactory.create("Salir", event -> {
                SceneManager.getStage().close();
            },
                null
        );

        VBox layout = new VBox(30, resumeButton, quitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(UIScale.getSide(), UIScale.getSide());

        return new Scene(layout);
    }
}
