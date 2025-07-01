package edu.asu.stratego.gui.core;

import edu.asu.stratego.gui.scene.BoardScene;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stage = new Stage();

    public static void setStage(Stage s) {
        stage = s;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void switchScene(Scene scene) {
        if (stage == null) {
            throw new IllegalStateException("Stage not initialized. Call setStage() first.");
        }
        stage.setScene(scene);
        System.out.println("CAMBIO DE ESCENA? Scene: " + stage.getTitle());
    }

    public static Scene getInitialScene() {
        return new BoardScene().getScene(); // Escena inicial del juego
    }

    public static void closeStage() {
        stage.close();
    }
}
