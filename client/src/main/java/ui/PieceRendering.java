package ui;

import DTOs.PieceDTO;
import chess.model.Piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static Enums.PieceType.WHITE_PAWN;

public class PieceRendering {

    private final Image img;
    private final PieceDTO piece;

    public PieceRendering(PieceDTO piece){
        this.piece = piece;
        try {
            this.img = getImage(); // Assumes image is already loaded
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g, int width, int height) {
        g.drawImage(img, 0, 0, width, height, null);
    }

    public Image getImage() throws IOException {
        String pathName = piece.getPieceType().getImagePathName();
//        System.out.println(pathName);
        return ImageIO.read(getClass().getResource(pathName));
    }
}
