package chess.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PieceImageLoader {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(PieceImageLoader.class.getResource(path));
        } catch (IOException e) {
            System.out.println("Could not load piece image: " + e.getMessage());
            return null;
        }
    }
}
