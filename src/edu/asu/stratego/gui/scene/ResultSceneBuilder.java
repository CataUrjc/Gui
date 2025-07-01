package edu.asu.stratego.gui.scene;

import edu.asu.stratego.gui.component.ButtonFactory;
import edu.asu.stratego.gui.handler.ResultActionHandler;
import edu.asu.stratego.gui.util.UIScale;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ResultSceneBuilder {

    public static Scene build(String message) {
        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(UIScale.getSide(), UIScale.getSide());

        Label resultLabel = new Label(message);
        resultLabel.setStyle("""
            -fx-font-size: 48px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
        """);

        layout.setStyle("-fx-background-color: #1d3557;");

        layout.getChildren().addAll(
                resultLabel,
                ButtonFactory.create("Volver al menú", ResultActionHandler::backToMenu),
                ButtonFactory.create("Salir", ResultActionHandler::exitApp)
        );

        return new Scene(layout);
    }
}
