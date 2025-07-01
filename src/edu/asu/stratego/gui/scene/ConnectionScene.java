package edu.asu.stratego.gui.scene;

import edu.asu.stratego.game.models.ClientSocket;
import edu.asu.stratego.game.models.Game;
import edu.asu.stratego.gui.component.ButtonFactory;
import edu.asu.stratego.gui.util.UIScale;
import edu.asu.stratego.media.ImageConstants;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;

/**
 * The initial scene for connecting to the game server.
 * Allows the player to enter their nickname and the server's IP address,
 * and manages the network connection to the game server.
 */
public class ConnectionScene {

    private static final Object LOGIN_LOCK = new Object();
    private static final int WIDTH = 800;
    private static final int HEIGHT = 300;

    private final Button connectButton = ButtonFactory.create("Enter Battlefield", e -> Platform.runLater(new SubmitConnectionInfo()), null);
    private final TextField nicknameField = createTextField();
    private final TextField serverIPField = createTextField();
    private static final Label statusLabel = createStatusLabel();

    private static String serverIP;
    private static String nickname;

    private final Scene scene;
    private final double unit = UIScale.getUnit();
    private final Font labelFont = Font.font("Russo One", FontWeight.BOLD, 14);

    /**
     * Constructs a {@code ConnectionScene}, initializing the connection interface.
     */
    public ConnectionScene() {
        GridPane form = buildForm();
        HBox background = buildBackground();
        BorderPane layout = buildLayout(background, form);

//        attachEvents();

        this.scene = new Scene(layout, WIDTH, HEIGHT);
    }

    /**
     * Creates and configures the background {@code GridPane} with the Stratego logo.
     *
     * @return The {@code GridPane} representing the background header.
     */
    private HBox buildBackground() {
        double unit = UIScale.getUnit();

        ImageView logo = new ImageView(ImageConstants.stratego_logo);
        logo.setFitWidth(unit * 4.4);
        logo.setFitHeight(unit * 1.25);

        HBox headerPane = new HBox();

        headerPane.setAlignment(Pos.CENTER);
        headerPane.getChildren().add(logo);
        headerPane.setPrefHeight(unit * 1.25 + 20);

        return headerPane;
    }

    /**
     * Returns the JavaFX {@code Scene} object representing the connection interface.
     *
     * @return The connection scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Creates and configures the input form for nickname and server IP.
     *
     * @return The {@code GridPane} containing the input fields and connect button.
     */
    private GridPane buildForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        Label nicknameLabel = createLabel("Nickname:");
        Label ipLabel = createLabel("Server IP:");

        grid.add(nicknameLabel, 0, 0);
        grid.add(nicknameField, 1, 0);
        grid.add(ipLabel, 0, 1);
        grid.add(serverIPField, 1, 1);
        grid.add(connectButton, 1, 3);

        GridPane.setHalignment(connectButton, HPos.RIGHT);
        return grid;
    }

    /**
     * Creates a styled {@code Label} with the given text.
     *
     * @param text The text for the label.
     * @return The created and styled {@code Label}.
     */
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(labelFont);
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Creates a styled {@code TextField}.
     *
     * @return The created and styled {@code TextField}.
     */
    private TextField createTextField() {
        TextField textField = new TextField();
        return textField;
    }

    /**
     * Creates a styled {@code Button} with the given text.
     *
     * @param text The text for the button.
     * @return The created and styled {@code Button}.
     */
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("""
            -fx-background-color: #e1ad01;
            -fx-text-fill: white;
            -fx-font-family: 'Verdana';
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 8px;
            -fx-cursor: hand;
        """);
        return button;
    }

    /**
     * Creates and configures the status {@code Label} at the bottom of the scene.
     *
     * @return The created status {@code Label}.
     */
    private static Label createStatusLabel() {
        Label label = new Label();
        label.setFont(Font.font("Russo One", 12));
        label.setTextFill(Color.YELLOW);
        return label;
    }

    /**
     * Creates the main layout {@code BorderPane} with the background and form.
     *
     * @param background The background {@code GridPane}.
     * @param form       The input form {@code GridPane}.
     * @return The {@code BorderPane} representing the main layout.
     */
    private BorderPane buildLayout(HBox background, GridPane form) {
        BorderPane border = new BorderPane();
        border.setTop(background);
        border.setCenter(form);
        border.setBottom(statusLabel);
        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        BorderPane.setMargin(statusLabel, new Insets(0, 0, 10, 0));
        border.setStyle(String.format("""
            -fx-background-image: url('edu/asu/stratego/media/images/board/setup_panel.png');
            -fx-background-size: %.2f %.2f;
            -fx-background-repeat: stretch;
        """, unit * 10, unit * 5));
        return border;
    }

    /**
     * Attaches the event handler to the connect button.
     */
    private void attachEvents() {
        connectButton.setOnAction(e -> Platform.runLater(new SubmitConnectionInfo()));
    }

    /**
     * A {@code Runnable} that processes the entered connection information
     * and notifies the connection thread.
     */
    private class SubmitConnectionInfo implements Runnable {
        @Override
        public void run() {
            Platform.runLater(() -> statusLabel.setText("Connecting to the server..."));

            nickname = nicknameField.getText().isBlank() ? "Player" : nicknameField.getText();
            serverIP = serverIPField.getText().isBlank() ? "localhost" : serverIPField.getText();

            Game.getPlayer().setNickname(nickname);

            setFormDisabled(true);

            synchronized (LOGIN_LOCK) {
                try {
                    LOGIN_LOCK.notify(); // Wake up ConnectToServer
                    LOGIN_LOCK.wait();   // Wait for connection attempt
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Platform.runLater(() -> statusLabel.setText("Connection interrupted"));
                }
            }

            setFormDisabled(false);
        }

        /**
         * Enables or disables the input form elements.
         *
         * @param disable {@code true} to disable, {@code false} to enable.
         */
        private void setFormDisabled(boolean disable) {
            nicknameField.setEditable(!disable);
            serverIPField.setEditable(!disable);
            connectButton.setDisable(disable);
        }
    }

    /**
     * A static {@code Runnable} that attempts to connect to the server
     * in a loop until a connection is successful.
     */
    public static class ConnectToServer implements Runnable {
        @Override
        public void run() {
            while (ClientSocket.getInstance() == null) {
                synchronized (LOGIN_LOCK) {
                    try {
                        LOGIN_LOCK.wait();
                        ClientSocket.connect(serverIP, 4212);
                    } catch (IOException | InterruptedException e) {
                        Platform.runLater(() -> statusLabel.setText("Cannot connect to the Server"));
                        if (e instanceof InterruptedException) {
                            Thread.currentThread().interrupt();
                            break; // Exit the loop if interrupted
                        }
                    } finally {
                        LOGIN_LOCK.notify();
                    }
                }
            }
        }
    }
}