package edu.asu.stratego.gui.board;

import edu.asu.stratego.gui.util.UIScale;
import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import edu.asu.stratego.game.models.Game;
import edu.asu.stratego.game.models.PieceColor;

public class BoardTurnIndicator {

    private static final Color RED  = new Color(0.48, 0.13, 0.13, 1.0);
    private static final Color BLUE = new Color(0.22, 0.24, 0.55, 1.0);
    private static final Object TURN_INDICATOR_TRIGGER = new Object();

    private static Rectangle turnIndicator;

    /**
     * Initializes the turn indicator with the color of the local player.
     * It also starts a background thread to update the indicator's color
     * whenever the game turn changes.
     */
    public BoardTurnIndicator() {
        int side = UIScale.getSide();
        turnIndicator = new Rectangle(0, 0, side, side);

        turnIndicator.setFill(Game.getPlayer().getColor() == PieceColor.RED ? RED : BLUE);

        Thread updateThread = new Thread(new TurnColorUpdater());
        updateThread.setDaemon(true);
        updateThread.start();
    }

    /**
     * Returns the graphical node of the indicator.
     *
     * @return the {@link Rectangle} used as the turn indicator
     */
    public static Rectangle getTurnIndicator() {
        return turnIndicator;
    }

    /**
     * Returns the synchronization object for turn changes.
     * This object is used to signal the turn indicator to update its color.
     *
     * @return the turn synchronization object
     */
    public static Object getTurnIndicatorTrigger() {
        return TURN_INDICATOR_TRIGGER;
    }

    /**
     * A {@code Runnable} responsible for updating the color of the indicator
     * when the game turn changes. It listens for notifications on the
     * {@link #TURN_INDICATOR_TRIGGER} object.
     */
    private static class TurnColorUpdater implements Runnable {
        @Override
        public void run() {
            synchronized (TURN_INDICATOR_TRIGGER) {
                while (true) {
                    try {
                        TURN_INDICATOR_TRIGGER.wait();

                        Platform.runLater(() -> {
                            PieceColor currentTurn = Game.getTurn();
                            Color currentFill = (Color) turnIndicator.getFill();

                            if (currentTurn == PieceColor.RED && !currentFill.equals(RED)) {
                                playColorTransition(BLUE, RED, 2000);
                            } else if (currentTurn == PieceColor.BLUE && !currentFill.equals(BLUE)) {
                                playColorTransition(RED, BLUE, 3000);
                            }
                        });
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("TurnColorUpdater interrupted: " + e.getMessage());
                        return;
                    }
                }
            }
        }

        /**
         * Plays a color transition animation on the turn indicator.
         *
         * @param from       The starting color of the transition.
         * @param to         The ending color of the transition.
         * @param durationMs The duration of the transition in milliseconds.
         */
        private void playColorTransition(Color from, Color to, int durationMs) {
            FillTransition transition = new FillTransition(
                    Duration.millis(durationMs),
                    turnIndicator,
                    from,
                    to
            );
            transition.play();
        }
    }
}