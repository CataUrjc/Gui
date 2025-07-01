package edu.asu.stratego.gui.core;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClientStageInitializer {

    public static void initialize(Stage primaryStage) {
        primaryStage.setTitle("Stratego");
        primaryStage.getIcons().add(new Image("/images/icon.png")); // Ruta desde resources
        primaryStage.setResizable(false);
        primaryStage.setScene(SceneManager.getInitialScene());
        primaryStage.show();

        SceneManager.setStage(primaryStage);
    }
}
