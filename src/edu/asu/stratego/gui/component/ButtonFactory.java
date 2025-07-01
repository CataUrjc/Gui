package edu.asu.stratego.gui.component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

import javafx.scene.image.ImageView; // Importa ImageView para mostrar la imagen

public class ButtonFactory {

    public static Button create(String text, EventHandler<ActionEvent> handler, ImageView buttonIcon) {

        Button button = new Button(text);
        button.setOnAction(handler);
        button.setStyle("""
            -fx-font-size: 14px;
            -fx-background-color: #e1ad01;
            -fx-text-fill: white;
            -fx-font-family: 'Verdana';
            -fx-font-weight: bold;
            -fx-background-radius: 10px;
            -fx-cursor: hand;
        """);

        if (buttonIcon != null){
            buttonIcon.setFitWidth(180); // Establece el tamaño de la imagen
            buttonIcon.setFitHeight(69);
            button.setGraphic(buttonIcon);
            button.setContentDisplay(ContentDisplay.BOTTOM);
            button.setPrefSize(200, 100);
        }
        return button;
    }
}
