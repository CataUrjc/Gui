package edu.asu.stratego.gui.setup;

import edu.asu.stratego.game.client.ClientGameManager;
import edu.asu.stratego.game.board.SetupBoard;
import edu.asu.stratego.game.models.Game;
import edu.asu.stratego.game.models.Piece;
import edu.asu.stratego.game.models.PieceColor;
import edu.asu.stratego.game.models.PieceType;
import edu.asu.stratego.gui.component.ButtonFactory;
import edu.asu.stratego.gui.scene.FormationScene;
import edu.asu.stratego.gui.util.UIScale;
import edu.asu.stratego.media.ImageConstants;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents the setup panel displayed at the top of the board during the
 * initial piece placement phase. It allows the player to view their available
 * pieces, get setup instructions, and finalize their setup.
 */
public class SetupPanel {

    private static final GridPane setupPanel = new GridPane();
    private static final GridPane piecePane = new GridPane();
    private static final Object updateReadyStatusLock = new Object();
    private static final StackPane instructionPane = new StackPane();
    private static final Label instructions = new Label();
    private static final Label readyLabel = new Label();
    private static final ImageView readyButton = new ImageView();

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Constructs and initializes the SetupPanel, including its header, body (piece selection),
     * and footer (instructions/ready button). It also starts a background thread
     * to manage the ready button's state.
     */
    public SetupPanel() {
        final double unit = UIScale.getUnit();

        setupPanel.setMaxSize(unit * 10, unit * 4);
        setupPanel.setStyle(String.format("""
                -fx-background-image: url('edu/asu/stratego/media/images/board/setup_panel.png');
                -fx-background-size: %.2f %.2f;
                -fx-background-repeat: stretch;
                """, unit * 10, unit * 5));

        setupPanel.add(createHeader(unit), 0, 0);
        setupPanel.add(createBody(unit), 0, 1);
        setupPanel.add(createFooter(unit), 0, 2);

        GridPane.setMargin(readyLabel, new Insets(unit * 0.8, 0, 0, unit * 1.5));
        readyLabel.setText("Waiting for opponent...");
        readyLabel.setFont(Font.font("Russo One", unit * 0.6));
        readyLabel.setTextFill(Color.ORANGE);

        executor.execute(this::updateReadyButtonLoop);
    }

    /**
     * Creates the header section of the SetupPanel, displaying the game logo,
     * a separator line, and player information including nicknames and the setup timer.
     *
     * @param unit The base unit for scaling UI elements.
     * @return The {@code GridPane} representing the header.
     */
    private GridPane createHeader(double unit) {
        System.out.println("SE HA CREADO EL HEADER");
        var logo = new ImageView(ImageConstants.stratego_logo);
        logo.setFitWidth(unit * 4.4);
        logo.setFitHeight(unit * 1.25);
        GridPane.setMargin(logo, new Insets(unit * 0.15, 0, 0, unit * 0.3));

        var headerLine = new Rectangle(unit * 0.04, unit * 1.25, new Color(0.4, 0.1, 0.0, 1.0));

        var headerText = new GridPane();
        headerText.getRowConstraints().add(new RowConstraints(unit * 0.6));
        GridPane.setMargin(headerText, new Insets(unit * 0.2, 0, 0, unit * 0.2));

        String titleContent = Game.getPlayer().getNickname() + " vs. " + Game.getOpponent().getNickname();
        double fontScale = 1.0 / ((titleContent.length() - 7) / 8 + 2);

        var nameDisplay = new Label(titleContent);
        nameDisplay.setFont(Font.font("Russo One", FontWeight.BOLD, unit * fontScale));
        nameDisplay.setTextFill(Color.ORANGE);
        nameDisplay.setAlignment(Pos.BOTTOM_LEFT);

        var setupTimer = new Label("Setup Time Left: ");
        setupTimer.setFont(Font.font("Russo One", unit / 3));
        setupTimer.setTextFill(new Color(0.9, 0.5, 0.0, 1.0));
        setupTimer.setAlignment(Pos.TOP_LEFT);

        var timer = new SetupTimer();
        timer.start();

        var timerPane = new GridPane();
        timerPane.setHgap(unit * 0.3);
        timerPane.setAlignment(Pos.CENTER_LEFT);

        timerPane.add(setupTimer, 0, 0);
        timerPane.add(timer.getLabel(), 1, 0);

        Button formationButton = ButtonFactory.create("Formation", e -> new FormationScene().show(), null);

//        Button loadGameButton = ButtonFactory.create("Load a Game", null, null);
//        loadGameButton.setOnAction(e -> {
//            FileChooser fc = new FileChooser();
//            fc.setTitle("Cargar partida");
//            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Partida (*.bin)", "*.bin"));
//            File file = fc.showOpenDialog(((Stage) loadGameButton.getScene().getWindow()));
//            if (file == null) return;
//
//            String path = file.getAbsolutePath();
//
//            Executors.newSingleThreadExecutor().execute(() -> {
//                try {
//                    ClientGameManager.loadGame(path);
//
//                    if (obj1 instanceof SetupBoard setup) {
//                        // Importante: actualizar la UI en el hilo JavaFX
//                        Platform.runLater(() -> {
//                            // Esto debe mostrar el tablero con las fichas
//                            SetupPanel.loadFrom(setup);
//                        });
//                    }
//
//                    // Si quieres, puedes también usar obj2 (turno) y obj3 (estado de juego)
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//            });
//        });

        var buttonBox = new HBox(unit * 0.5);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.getChildren().addAll(formationButton/*, loadGameButton*/);
        GridPane.setMargin(buttonBox, new Insets(unit * 0.1, 0, 0, 0));

        headerText.add(nameDisplay, 0, 0);
        headerText.add(timerPane, 0, 1);
        headerText.add(buttonBox, 0, 2);

        var headerPane = new GridPane();
        headerPane.getColumnConstraints().add(new ColumnConstraints(unit * 5));
        headerPane.addRow(0, logo, headerLine, headerText);

        return headerPane;
    }

    /**
     * Creates the body section of the SetupPanel, which contains the selectable
     * piece images and their respective counts.
     *
     * @param unit The base unit for scaling UI elements.
     * @return The {@code GridPane} representing the piece selection area.
     */
    private GridPane createBody(double unit) {
        var pieces = new SetupPieces();
        var pieceImages = pieces.getPieceImages();
        var pieceCount = pieces.getPieceCountLabels();

        GridPane.setMargin(piecePane, new Insets(unit * 0.15, 0, 0, unit * 0.15));

        for (int i = 0; i < 12; ++i) {
            piecePane.add(pieceImages[i], i, 0);
            piecePane.add(pieceCount[i], i, 1);
        }

        return piecePane;
    }

    /**
     * Creates the footer section of the SetupPanel, which initially displays
     * setup instructions and later transforms into the "Ready" button when all
     * pieces are placed.
     *
     * @param unit The base unit for scaling UI elements.
     * @return The {@code StackPane} representing the footer.
     */
    private StackPane createFooter(double unit) {
        GridPane.setMargin(instructionPane, new Insets(unit * 0.15, 0, 0, 0));

        instructions.setText("""
                place a piece: select a piece above and click on the board
                   remove a piece: click on an existing piece on the board
                """);
        instructions.setFont(Font.font("Russo One", unit * 0.3));
        instructions.setTextFill(Color.ORANGE);

        readyButton.setImage(ImageConstants.READY_IDLE);
        readyButton.setFitWidth(unit * 2.25);
        readyButton.setFitHeight(unit * 0.75);

        readyButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                e -> readyButton.setImage(ImageConstants.READY_HOVER));
        readyButton.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                e -> readyButton.setImage(ImageConstants.READY_IDLE));
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> Platform.runLater(SetupPanel::finishSetup));

        instructionPane.getChildren().add(instructions);
        instructionPane.setAlignment(Pos.CENTER);

        return instructionPane;
    }

    /**
     * Returns the lock object used to synchronize updates to the ready status
     * of the setup panel.
     *
     * @return The {@code Object} used as a lock for ready status updates.
     */
    public static Object getUpdateReadyStatus() {
        return updateReadyStatusLock;
    }

    /**
     * Returns the {@code GridPane} representing the entire setup panel.
     *
     * @return The setup panel {@code GridPane}.
     */
    public static GridPane getSetupPanel() {
        return setupPanel;
    }

    /**
     * Finalizes the player's setup by removing the piece selection and
     * instruction areas and displaying a "Waiting for opponent..." message.
     * It also notifies the {@link ClientGameManager} that the setup is complete.
     */
    public static void finishSetup() {
        System.out.println("He llegado hasta aqui 5." + ClientGameManager.getSetupPieces());
        Object setupPieces = ClientGameManager.getSetupPieces();
        synchronized (setupPieces) {
            setupPanel.getChildren().removeAll(instructionPane, piecePane);
            setupPanel.add(readyLabel, 0, 1);
            setupPieces.notify();
        }
    }

    /**
     * A background loop that monitors the player's piece placement status
     * and updates the footer of the SetupPanel to display the "Ready" button
     * when all pieces have been placed.
     */
    private void updateReadyButtonLoop() {
        boolean readyState = false;
        synchronized (updateReadyStatusLock) {
            while (true) {
                try {
                    updateReadyStatusLock.wait();

                    boolean allPlaced = SetupPieces.getAllPiecesPlaced();
                    if (allPlaced && !readyState) {
                        Platform.runLater(() -> {
                            instructionPane.getChildren().setAll(readyButton);
                        });
                        readyState = true;
                    } else if (!allPlaced && readyState) {
                        Platform.runLater(() -> {
                            instructionPane.getChildren().setAll(instructions);
                        });
                        readyState = false;
                    }
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Loads a pre-existing setup onto the setup panel. This is used when
     * a player rejoins a game during the setup phase.
     *
     * @param setup The {@link SetupBoard} containing the player's current piece placements.
     */
    public static void loadFrom(SetupBoard setup) {
        setupPanel.getChildren().clear();

        final double unit = UIScale.getUnit();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 10; col++) {
                Piece piece = setup.getPiece(row, col);
                if (piece != null) {
                    PieceType type = piece.getPieceType();
                    PieceColor color = piece.getPieceColor();

                    String path = ImageConstants.getImagePath(type, color);
                    ImageView imageView = new ImageView(path);
                    imageView.setFitWidth(unit);
                    imageView.setFitHeight(unit);

                    setupPanel.add(imageView, col, row);
                }
            }
        }
    }
}