package edu.asu.stratego.gui.board;

import edu.asu.stratego.game.models.Game;
import edu.asu.stratego.gui.board.indicator.TurnIndicatorInitializer;
import edu.asu.stratego.gui.scene.PauseScene;
import edu.asu.stratego.gui.setup.SetupPanel;
import edu.asu.stratego.gui.component.ButtonFactory;
import edu.asu.stratego.gui.util.UIScale;
import edu.asu.stratego.media.ImageConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class BoardSceneAssembler {

    public static StackPane assemble() {
        StackPane root = new StackPane();

        Rectangle background = TurnIndicatorInitializer.initialize();
        GridPane setupPanel = createSetupPanel();
        ImageView border = createBoardBorder();
        Button pauseButton = ButtonFactory.create("⏸", e -> PauseScene.show(), null);
        StackPane.setAlignment(pauseButton, Pos.TOP_RIGHT);
        StackPane.setMargin(pauseButton, new Insets(10));

        root.getChildren().addAll(
                background,
                Game.getBoard().getPiecePane(),
                Game.getBoard().getEventPane(),
                setupPanel,
                border,
                pauseButton
        );

        root.setMaxSize(UIScale.getSide(), UIScale.getSide());
        Game.getBoard().getPiecePane().setAlignment(Pos.CENTER);
        Game.getBoard().getEventPane().setAlignment(Pos.CENTER);

        return root;
    }

    private static GridPane createSetupPanel() {
        new SetupPanel(); // Initializes setup panel
        GridPane panel = SetupPanel.getSetupPanel();
        StackPane.setMargin(panel, new Insets(UIScale.getUnit(), 0, 0, 0));
        StackPane.setAlignment(panel, Pos.TOP_CENTER);
        return panel;
    }

    private static ImageView createBoardBorder() {
        ImageView border = new ImageView(ImageConstants.BORDER);
        border.setFitHeight(UIScale.getSide());
        border.setFitWidth(UIScale.getSide());
        StackPane.setAlignment(border, Pos.CENTER);
        return border;
    }
}
