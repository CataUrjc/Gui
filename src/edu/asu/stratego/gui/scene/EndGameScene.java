package edu.asu.stratego.gui.scene;

import edu.asu.stratego.game.models.Game;
import edu.asu.stratego.game.models.GameStatus;
import edu.asu.stratego.game.models.PieceColor;
import edu.asu.stratego.game.models.Player;
import edu.asu.stratego.gui.util.UIScale;
import edu.asu.stratego.media.ImageConstants;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the scene displayed at the end of a game, showing the winner and loser.
 */
public class EndGameScene {
    private static final int WIDTH = 420;
    private static final int HEIGHT = 400;
    private final Scene scene;
    private final Font titleFont = Font.font("Russo One", FontWeight.BOLD, 24);
    private final Font nameFont = Font.font("Russo One", FontWeight.BOLD, 32);
    private final double unit = UIScale.getUnit();
    private final String buttonStyle = """
        -fx-background-color: #e1ad01;
        -fx-text-fill: white;
        -fx-font-family: 'Verdana';
        -fx-font-size: 14px;
        -fx-font-weight: bold;
        -fx-background-radius: 8px;
        -fx-cursor: hand;
    """;

    /**
     * Constructs an {@code EndGameScene} based on the final {@code GameStatus}.
     * Displays the winner and loser of the game and provides a button to close the application.
     *
     * @param status The final {@code GameStatus} indicating the reason for the game ending.
     */
    public EndGameScene(GameStatus status) {
        GridPane background = buildBackground();
        List<Player> loserAndWinner = determineLoserAndWinner(status);
        Player loser = loserAndWinner.get(0);
        Player winner = loserAndWinner.get(1);

        Label winnerTitle = createLabel("Winner:", titleFont, Color.WHITE);
        Label winnerName = createPlayerLabel(winner.getNickname(), nameFont, winner.getColor());
        Label loserTitle = createLabel("Loser:", titleFont, Color.WHITE);
        Label loserName = createPlayerLabel(loser.getNickname(), nameFont, loser.getColor());

        Button exitButton = createExitButton();

        VBox layout = new VBox(10, background, winnerTitle, winnerName, loserTitle, loserName, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle(String.format("""
            -fx-background-image: url('edu/asu/stratego/media/images/board/setup_panel.png');
            -fx-background-size: %.2f %.2f;
            -fx-background-repeat: stretch;
        """, unit * 20, unit * 5));

        this.scene = new Scene(layout, WIDTH, HEIGHT);
    }

    /**
     * Returns the JavaFX {@code Scene} object representing the end game screen.
     *
     * @return The end game scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Determines the loser and winner based on the final {@code GameStatus}.
     * The order in the returned list is [loser, winner].
     *
     * @param status The final {@code GameStatus} of the game.
     * @return A {@code List} containing the loser and the winner {@code Player} objects.
     */
    private List<Player> determineLoserAndWinner(GameStatus status) {
        Player bluePlayer;
        Player redPlayer;

        if (Game.getPlayer().getColor() == PieceColor.RED) {
            redPlayer = Game.getPlayer();
            bluePlayer = Game.getOpponent();
        } else {
            redPlayer = Game.getOpponent();
            bluePlayer = Game.getPlayer();
        }

        List<Player> result = new ArrayList<>();
        if (status == GameStatus.BLUE_NO_MOVES || status == GameStatus.BLUE_CAPTURED) {
            result.add(bluePlayer); // loser
            result.add(redPlayer);  // winner
        } else {
            result.add(redPlayer); // loser
            result.add(bluePlayer); // winner
        }
        return result;
    }

    /**
     * Creates and configures a {@code Label} with the specified text, font, and color.
     *
     * @param text  The text for the label.
     * @param font  The font for the label.
     * @param color The color of the text.
     * @return The created and styled {@code Label}.
     */
    private Label createLabel(String text, Font font, Color color) {
        Label label = new Label(text);
        label.setFont(font);
        label.setTextFill(color);
        return label;
    }

    /**
     * Creates a {@code Label} specifically for displaying player nicknames with their respective colors.
     *
     * @param nickname    The nickname of the player.
     * @param font        The font for the nickname.
     * @param pieceColor The color associated with the player.
     * @return The created and styled {@code Label}.
     */
    private Label createPlayerLabel(String nickname, Font font, PieceColor pieceColor) {
        Label label = new Label(nickname);
        label.setFont(font);
        label.setTextFill(pieceColor == PieceColor.RED ? Color.RED : Color.BLUE);
        return label;
    }

    /**
     * Creates and configures the "Close" button for the end game scene.
     *
     * @return The created and styled "Close" {@code Button}.
     */
    private Button createExitButton() {
        Button exitButton = new Button("Close");
        exitButton.setOnAction(e -> Platform.exit());
        exitButton.setStyle(buttonStyle);
        return exitButton;
    }

    /**
     * Builds the background {@code GridPane} containing the Stratego logo.
     *
     * @return The {@code GridPane} representing the background header.
     */
    private GridPane buildBackground() {
        ImageView logo = new ImageView(ImageConstants.stratego_logo);
        logo.setFitWidth(unit * 4.4);
        logo.setFitHeight(unit * 1.25);
        GridPane.setMargin(logo, new Insets(unit * 0.15, 0, 0, unit * 0.3));
        GridPane.setHalignment(logo, HPos.CENTER);

        GridPane headerPane = new GridPane();
        headerPane.getColumnConstraints().add(new ColumnConstraints(unit * 5));
        headerPane.addRow(0, logo);
        return headerPane;
    }
}