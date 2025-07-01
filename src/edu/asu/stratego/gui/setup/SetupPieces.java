package edu.asu.stratego.gui.setup;

import edu.asu.stratego.game.models.Game;
import edu.asu.stratego.game.models.PieceType;
import edu.asu.stratego.gui.util.UIScale;
import edu.asu.stratego.util.HashTables;
import edu.asu.stratego.util.MutableBoolean;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.EnumMap;

/**
 * Represents the interactive pieces displayed in the SetupPanel, allowing
 * the player to select and manage their unit deployment during the setup phase.
 */
public class SetupPieces {

    private static final int PIECE_COUNT = PieceType.values().length;
    private static final double UNIT = UIScale.getUnit();

    private static final EnumMap<PieceType, MutableBoolean> pieceSelected = new EnumMap<>(PieceType.class);
    private static final EnumMap<PieceType, Integer> availability = new EnumMap<>(PieceType.class);
    private static final EnumMap<PieceType, ImageView> pieceImages = new EnumMap<>(PieceType.class);
    private static final EnumMap<PieceType, Label> pieceCount = new EnumMap<>(PieceType.class);

    private static PieceType selectedPieceType = null;
    private static final ColorAdjust zeroPiecesEffect = new ColorAdjust(-1.0, 0.0, 0.0, 0.0);
    private static boolean allPiecesPlaced = false;

    private static final String[] PIECE_SUFFIX = {
            "02", "03", "04", "05", "06", "07", "08", "09", "10", "BOMB", "SPY", "FLAG"
    };

    private static final int[] INITIAL_PIECE_COUNTS = {
            8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 1, 1
    };

    /**
     * Initializes the SetupPieces by creating the visual representations
     * and tracking information for each selectable piece type.
     */
    public SetupPieces() {
        String playerColor = Game.getPlayer().getColor().toString();

        for (int i = 0; i < PIECE_COUNT; ++i) {
            PieceType type = PieceType.values()[i];
            int count = INITIAL_PIECE_COUNTS[i];
            availability.put(type, count);

            Label countLabel = createPieceCountLabel(count);
            pieceCount.put(type, countLabel);

            ImageView image = createPieceImage(playerColor, PIECE_SUFFIX[i], i);
            pieceImages.put(type, image);

            pieceSelected.put(type, new MutableBoolean(false));
        }
    }

    /**
     * Creates a {@code Label} to display the current count of a specific piece type.
     *
     * @param count The initial number of available pieces of this type.
     * @return The created and styled {@code Label}.
     */
    private Label createPieceCountLabel(int count) {
        Label label = new Label(" x" + count);
        label.setFont(Font.font("Russo One", UNIT * 0.4));
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Creates an {@code ImageView} for a specific piece, sets its appropriate
     * image based on the player's color and piece type, positions it in the
     * setup grid, and attaches an event handler to manage piece selection.
     *
     * @param playerColor The color of the current player.
     * @param suffix      The suffix used to identify the piece's image resource.
     * @param column      The column index where this piece is located in the setup grid.
     * @return The created {@code ImageView} for the piece.
     */
    private ImageView createPieceImage(String playerColor, String suffix, int column) {
        ImageView image = new ImageView(HashTables.PIECE_MAP.get(playerColor + "_" + suffix));
        image.setFitHeight(UNIT * 0.8);
        image.setFitWidth(UNIT * 0.8);
        GridPane.setColumnIndex(image, column);
        image.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectPieceHandler());
        return image;
    }

    /**
     * Handles mouse press events on the piece images in the SetupPanel.
     * It manages the selection and deselection of pieces, updating the
     * visual appearance (glow effect) and the {@code selectedPieceType}.
     */
    private static class SelectPieceHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            ImageView clickedImage = (ImageView) event.getSource();

            for (PieceType type : PieceType.values()) {
                boolean isClicked = pieceImages.get(type) == clickedImage;

                if (!isClicked) {
                    if (availability.get(type) > 0) {
                        pieceImages.get(type).setEffect(new Glow(0.0));
                    }
                    pieceSelected.get(type).setFalse();
                } else {
                    boolean alreadySelected = pieceSelected.get(type).getValue();
                    if (!alreadySelected && availability.get(type) > 0) {
                        selectedPieceType = type;
                        clickedImage.setEffect(new Glow(1.0));
                        pieceSelected.get(type).setTrue();
                    } else {
                        selectedPieceType = null;
                        if (availability.get(type) > 0) {
                            clickedImage.setEffect(new Glow(0.0));
                        }
                        pieceSelected.get(type).setFalse();
                    }
                }
            }
        }
    }

    /**
     * Returns the {@code PieceType} that is currently selected by the player
     * in the SetupPanel.
     *
     * @return The selected {@code PieceType}, or {@code null} if no piece is selected.
     */
    public static PieceType getSelectedPieceType() {
        return selectedPieceType;
    }

    /**
     * Returns the number of available pieces of a specific {@code PieceType}
     * that the player can still place on the board.
     *
     * @param type The {@code PieceType} to query.
     * @return The number of available pieces of the given type.
     */
    public static int getPieceCount(PieceType type) {
        return availability.getOrDefault(type, 0);
    }

    /**
     * Increments the count of available pieces for a given {@code PieceType}
     * and updates the corresponding count label and visual effect. This is
     * typically called when a piece is removed from the setup board.
     *
     * @param type The {@code PieceType} to increment.
     */
    public static void incrementPieceCount(PieceType type) {
        int updatedCount = availability.get(type) + 1;
        availability.put(type, updatedCount);
        pieceCount.get(type).setText(" x" + updatedCount);

        if (updatedCount == 1) {
            pieceImages.get(type).setEffect(new Glow(0.0));
        }

        allPiecesPlaced = false;
        notifyReadyStatus();
    }

    /**
     * Decrements the count of available pieces for a given {@code PieceType}
     * and updates the corresponding count label and visual effect. This is
     * typically called when a piece is placed on the setup board.
     *
     * @param type The {@code PieceType} to decrement.
     */
    public static void decrementPieceCount(PieceType type) {
        int updatedCount = availability.get(type) - 1;
        availability.put(type, updatedCount);
        pieceCount.get(type).setText(" x" + updatedCount);

        if (updatedCount == 0) {
            pieceImages.get(type).setEffect(zeroPiecesEffect);
            pieceSelected.get(type).setFalse();
            selectedPieceType = null;
        }

        allPiecesPlaced = availability.values().stream().allMatch(count -> count == 0);
        notifyReadyStatus();
    }

    /**
     * Returns a boolean indicating whether all of the player's pieces have been
     * placed on the setup board.
     *
     * @return {@code true} if all pieces are placed, {@code false} otherwise.
     */
    public static boolean getAllPiecesPlaced() {
        return allPiecesPlaced;
    }

    /**
     * Returns an array containing all the {@code ImageView} nodes representing
     * the selectable pieces in the SetupPanel.
     *
     * @return An array of {@code ImageView} objects for each piece type.
     */
    public ImageView[] getPieceImages() {
        return Arrays.stream(PieceType.values())
                .map(pieceImages::get)
                .toArray(ImageView[]::new);
    }

    /**
     * Returns an array containing all the {@code Label} nodes displaying the
     * count of each piece type in the SetupPanel.
     *
     * @return An array of {@code Label} objects for the count of each piece type.
     */
    public Label[] getPieceCountLabels() {
        return Arrays.stream(PieceType.values())
                .map(pieceCount::get)
                .toArray(Label[]::new);
    }

    /**
     * Notifies the {@link SetupPanel} that the player's ready status might have
     * changed (either by placing the last piece or removing a piece). This
     * allows the SetupPanel to update its UI accordingly (e.g., enabling the
     * "Ready" button).
     */
    private static void notifyReadyStatus() {
        Object monitor = SetupPanel.getUpdateReadyStatus();
        synchronized (monitor) {
            monitor.notify();
        }
    }
}