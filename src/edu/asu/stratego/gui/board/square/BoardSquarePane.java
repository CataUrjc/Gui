package edu.asu.stratego.gui.board.square;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Representación gráfica de una casilla individual del tablero de Stratego.
 * Utiliza un {@link StackPane} para superponer el fondo y la pieza.
 */
public class BoardSquarePane extends StackPane {

    private final ImageView pieceImage;

    /**
     * Crea una casilla del tablero con el tipo especificado.
     *
     * @param type tipo de casilla (clara u oscura)
     */
    public BoardSquarePane(BoardSquareType type) {
        this.pieceImage = new ImageView();
        setBackgroundStyle(type);
        getChildren().add(pieceImage);
    }

    /**
     * Devuelve la vista de la pieza en esta casilla.
     *
     * @return el {@link ImageView} que contiene la imagen de la pieza
     */
    public ImageView getPiece() {
        return pieceImage;
    }

    /**
     * Establece la imagen de la pieza en esta casilla.
     *
     * @param piece imagen de la pieza a mostrar
     */
    public void setPiece(Image piece) {
        pieceImage.setImage(piece);
    }

    /**
     * Aplica el estilo de fondo según el tipo de casilla.
     *
     * @param type tipo de casilla (clara u oscura)
     */
    private void setBackgroundStyle(BoardSquareType type) {
        String imageUrl = switch (type) {
            case LIGHT -> "url(edu/asu/stratego/media/images/board/grass1.png)";
            case DARK  -> "url(edu/asu/stratego/media/images/board/grass2.png)";
        };
        setStyle("-fx-background-image: " + imageUrl);
    }
}
