package edu.asu.stratego.gui.scene;

import edu.asu.stratego.gui.util.UIScale;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormationSceneBuilder {
    public static VBox build(Button defensiveBtn, Button offensiveBtn, Button balancedBtn, Button closeBtn) {
        // Contenedor principal del layout
        VBox layout = new VBox(25); // Espacio entre elementos
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(UIScale.getSide(), UIScale.getSide());
        // Crea un Label para el título de la escena
        Label sceneTitle = new Label("Seleccionar Formación");
        sceneTitle.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Crea un HBox para colocar los tres botones de formación en una fila
        HBox formationButtonsRow = new HBox(15); // Espacio entre botones en la fila
        formationButtonsRow.setAlignment(Pos.CENTER);
        formationButtonsRow.getChildren().addAll(defensiveBtn, offensiveBtn, balancedBtn);

        // Añade el título, la fila de botones y el botón de cerrar al VBox principal
        layout.getChildren().addAll(sceneTitle, formationButtonsRow, closeBtn);

        // Establece el estilo de fondo para el layout
        layout.setStyle(String.format("""
            -fx-background-image: url('edu/asu/stratego/media/images/board/setup_panel.png');
            -fx-background-size: %.2f %.2f;
            -fx-background-repeat: stretch;
            -fx-text-fill: white;
            -fx-padding: 20px;
        """, UIScale.getUnit() * 15, UIScale.getUnit() * 5));

        return layout;
    }
}
