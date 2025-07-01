package edu.asu.stratego.gui.stage;

import edu.asu.stratego.game.board.ServerBoard;
import edu.asu.stratego.game.client.ClientGameManager; // Might be useful later
import edu.asu.stratego.game.client.GameConnectionManager;
import edu.asu.stratego.game.server.ServerGameManager;
import edu.asu.stratego.gui.component.ButtonFactory;
import edu.asu.stratego.gui.core.SceneManager;
import edu.asu.stratego.gui.util.UIScale;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

public class PauseStage extends Stage {

    // 1. Add a field to hold the GameConnectionManager instance
    private GameConnectionManager gameConnectionManager;

    // 2. Modify the constructor to accept the GameConnectionManager
    public PauseStage(GameConnectionManager gameConnectionManager) {
        this.gameConnectionManager = gameConnectionManager; // Store the instance

        this.initOwner(SceneManager.getStage());
        this.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(UIScale.getSide(), UIScale.getSide());

        Button resumeButton = ButtonFactory.create("Resume", event ->
                        this.close(),
                null
        );

        Button saveButton = ButtonFactory.create("Save", event -> {
                    FileChooser fc = new FileChooser();
                    fc.setTitle("Guardar partida");
                    fc.getExtensionFilters().add(
                            new FileChooser.ExtensionFilter("Partida (*.bin)", "*.bin")
                    );
                    File fichero = fc.showSaveDialog(this);
                    if (fichero == null) return;

                    String ruta = fichero.getAbsolutePath();

                    Executors.newSingleThreadExecutor().execute(() -> {
                        try {
                            // 3. Use the stored instance instead of creating a new one
                            if (this.gameConnectionManager != null) {
                                this.gameConnectionManager.saveGame(ruta);
                            } else {
                                System.err.println("Error: GameConnectionManager is not initialized in PauseStage.");
                                // Optionally, show an alert to the user
                            }
                        } catch (IOException ex) {
                            // It's better to show an alert to the user here instead of throwing a RuntimeException
                            System.err.println("Error saving game: " + ex.getMessage());
                            // Optionally, update UI to show save failed
                        }
                    });
                },
                null
        );

        Button loadButton = ButtonFactory.create("Load", event -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Cargar partida");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Partida (*.bin)", "*.bin")
            );
            File fichero = fc.showOpenDialog(this); // showOpenDialog para cargar
            if (fichero == null) return;

            String ruta = fichero.getAbsolutePath();

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    if (this.gameConnectionManager != null) {
                        this.gameConnectionManager.loadGame(ruta); // <-- Envía la solicitud de carga
                        // Después de enviar la solicitud, ¡espera la respuesta del servidor!
                        Object loadedObject = this.gameConnectionManager.readObject();
                        // El servidor debería enviar el Board cargado como respuesta
                        if (loadedObject instanceof ServerBoard) { // Asegúrate de usar la ruta completa de tu Board
                            ServerBoard loadedBoard = (ServerBoard) loadedObject;

                            System.out.println("Partida cargada exitosamente.");

                            javafx.application.Platform.runLater(this::close);

                        } else {
                            System.err.println("Objeto recibido no es un Board: " + loadedObject.getClass().getName());
                            // Informar al usuario que el archivo no era válido o hubo un error.
                        }
                    } else {
                        System.err.println("Error: GameConnectionManager no inicializado para cargar.");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    System.err.println("Error al cargar la partida: " + ex.getMessage());
                    // Informar al usuario que la carga falló.
                }
            });
        }, null);

        layout.getChildren().addAll(resumeButton, saveButton, loadButton);

        layout.setStyle(String.format("""
            -fx-background-image: url('edu/asu/stratego/media/images/board/setup_panel.png');
            -fx-background-size: %.2f %.2f;
            -fx-background-repeat: stretch;
            -fx-background-color: #2b2b2b;
            -fx-border-color: #e1ad01;
            -fx-border-width: 4px;
            -fx-border-radius: 10px;
            -fx-background-radius: 10px;
        """, UIScale.getUnit() * 10, UIScale.getUnit() * 5));

        Scene pauseScene = new Scene(layout, 250, 200);

        this.setScene(pauseScene);
        this.setTitle("Game Paused");
        this.setResizable(true);
    }
}