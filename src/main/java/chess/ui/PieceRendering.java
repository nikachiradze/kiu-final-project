package chess.ui;

import chess.model.Piece;

import java.awt.*;

public class PieceRendering {

    private final Image img;
    private final Piece piece;

    public PieceRendering(Piece piece) {
        this.piece = piece;
        this.img = piece.getImage();  // Assumes image is already loaded
    }

    public void draw(Graphics g, int width, int height) {
        g.drawImage(img, 0, 0, width, height, null);
    }
}
