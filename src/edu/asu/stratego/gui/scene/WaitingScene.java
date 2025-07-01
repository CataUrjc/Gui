package edu.asu.stratego.gui.scene;

import edu.asu.stratego.gui.util.UIScale;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WaitingScene {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 150;

    private final Scene scene;
    /**
     * Constructs a new {@code WaitingScene}.
     * Initializes the scene with a "Waiting for an opponent..." label
     * on a background image.
     */
    public WaitingScene() {
        double unit = UIScale.getUnit();

        Label wait = new Label("Waiting for an opponent...");
        wait.setFont(Font.font("Russo One", FontWeight.BOLD, 14));
        wait.setTextFill(Color.WHITE);

        StackPane layout = new StackPane(wait);
        layout.setStyle(String.format("""
            -fx-background-image: url('edu/asu/stratego/media/images/board/setup_panel.png');
            -fx-background-size: %.2f %.2f;
            -fx-background-repeat: stretch;
            -fx-text-color: white
        """, unit * 15, unit * 5));

        this.scene = new Scene(layout, WIDTH, HEIGHT);
        this.scene.setFill(Color.LIGHTGRAY);
    }
    /**
     * Returns the JavaFX {@code Scene} object representing the waiting scene.
     *
     * @return The waiting scene.
     */
    public Scene getScene() {
        return scene;
    }
}
