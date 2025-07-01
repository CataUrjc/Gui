package edu.asu.stratego.gui.board;

import edu.asu.stratego.media.ImageConstants;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import static edu.asu.stratego.gui.stage.ClientStage.getSide;

public class BoardBorderFactory {

    public static ImageView create() {
        ImageView border = new ImageView(ImageConstants.BORDER);
        border.setFitHeight(getSide());
        border.setFitWidth(getSide());
        StackPane.setAlignment(border, Pos.CENTER);
        return border;
    }
}
