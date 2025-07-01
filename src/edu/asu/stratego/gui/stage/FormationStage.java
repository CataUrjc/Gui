package edu.asu.stratego.gui.stage;

import edu.asu.stratego.game.models.Game;
import edu.asu.stratego.gui.component.ButtonFactory;
import edu.asu.stratego.gui.core.SceneManager;
import edu.asu.stratego.gui.handler.FormationActionHandler;
import edu.asu.stratego.gui.scene.FormationSceneBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;      // Importa Image para cargar la imagen
import javafx.scene.image.ImageView; // Importa ImageView para mostrar la imagen
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Manages the modal window for the piece formation setup.
 * This class respects the Single Responsibility Principle by handling
 * only the creation and configuration of its own Stage.
 */
public class FormationStage extends Stage {

    public FormationStage() {
        this.initOwner(SceneManager.getStage());
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Seleccionar Formación");

        Button defensiveBtn = ButtonFactory.create("Defensive", e ->{
                    FormationActionHandler.applyDefensive(Game.getBoard(), Game.getPlayer().getColor());
                    this.close();
                },
                new ImageView(new Image("edu/asu/stratego/media/images/board/deffesive_formation.png"))
        );

        Button offensiveBtn = ButtonFactory.create("Offensive", e ->{
                FormationActionHandler.applyOffensive(Game.getBoard(), Game.getPlayer().getColor());
                this.close();
            },
            new ImageView(new Image("edu/asu/stratego/media/images/board/offensive_formation.png"))
        );

        Button balancedBtn = ButtonFactory.create("Balanced", e ->{
                    FormationActionHandler.applyBalanced(Game.getBoard(), Game.getPlayer().getColor());
                    this.close();
                },
                new ImageView(new Image("edu/asu/stratego/media/images/board/balanced_formation.png"))
        );

        Button closeBtn = ButtonFactory.create("Cerrar", e ->
                this.close(),
                null
        );

        // Crea la escena y la asigna al stage
        Scene formationScene = new Scene(FormationSceneBuilder.build(defensiveBtn, offensiveBtn, balancedBtn, closeBtn), 800, 300);
        this.setScene(formationScene);
        this.setResizable(true);
    }
}