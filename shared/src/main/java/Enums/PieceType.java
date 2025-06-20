package Enums;

import javax.imageio.ImageIO;
import java.awt.*;

public enum PieceType {
    WHITE_KNIGHT,
    BLACK_KNIGHT,
    WHITE_BISHOP,
    BLACK_BISHOP,
    WHITE_ROOK,
    BLACK_ROOK,
    WHITE_QUEEN,
    BLACK_QUEEN,
    WHITE_KING,
    BLACK_KING,
    WHITE_PAWN,
    BLACK_PAWN;

    public String getImagePathName() {
        return switch (this){
            case WHITE_KNIGHT -> "/wknight.png";
            case BLACK_KNIGHT -> "/bknight.png";
            case WHITE_BISHOP -> "/wbishop.png";
            case BLACK_BISHOP -> "/bbishop.png";
            case WHITE_ROOK -> "/wrook.png";
            case BLACK_ROOK -> "/brook.png";
            case WHITE_QUEEN -> "/wq.png";
            case BLACK_QUEEN -> "/bq.png";
            case WHITE_KING -> "/wk.png";
            case BLACK_KING -> "/bk.png";
            case WHITE_PAWN -> "/wp.png";
            case BLACK_PAWN -> "/bp.png";
        };
    }
}
