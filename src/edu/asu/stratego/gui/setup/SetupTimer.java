package edu.asu.stratego.gui.setup;

import edu.asu.stratego.gui.board.FormationManager;
import edu.asu.stratego.gui.util.UIScale;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * A setup timer that counts down from a fixed start time and optionally notifies a callback on finish.
 */
public class SetupTimer {

    private static final int START_TIME = 300;

    private final Label timerLabel;
    private final IntegerProperty secondsLeft;
    private Timeline timeline;
    private Runnable onTimerFinished;

    public SetupTimer() {
        final double UNIT = UIScale.getUnit();

        this.timerLabel = new Label();
        this.secondsLeft = new SimpleIntegerProperty(START_TIME);

        this.timerLabel.textProperty().bind(secondsLeft.asString());
        this.timerLabel.setFont(Font.font("Russo One", UNIT / 3));
        this.timerLabel.setTextFill(Color.web("#e68000")); // (0.9, 0.5, 0.0, 1.0)
        this.timerLabel.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Starts the countdown timer from START_TIME to 0.
     */
    public void start() {
        if (timeline != null) {
            timeline.stop();
        }

        secondsLeft.set(START_TIME);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(START_TIME + 1), new KeyValue(secondsLeft, 0))
        );
        timeline.setOnFinished(new TimerFinished());
        timeline.playFromStart();
    }

    /**
     * Sets a callback to be invoked when the timer finishes.
     * @param onFinished Runnable callback
     */
    private class TimerFinished implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FormationManager.randomSetup();
        }
    }

    /**
     * @return Label showing the seconds left
     */
    public Label getLabel() {
        return timerLabel;
    }
}
